package client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.gudy.azureus2.core3.disk.DiskManagerFileInfo;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.download.DownloadManagerListener;
import org.gudy.azureus2.core3.download.DownloadManagerTrackerListener;
import org.gudy.azureus2.core3.global.GlobalManager;
import org.gudy.azureus2.core3.tracker.client.TRTrackerAnnouncerResponse;
import org.gudy.azureus2.core3.tracker.client.TRTrackerScraperResponse;

import com.aelitis.azureus.core.AzureusCore;
import com.aelitis.azureus.core.AzureusCoreFactory;

/**
 * The <code>ClientTorrentManager</code> class is the main 'engine' 
 * of the client torrent application. It has methods to start a torrent download, 
 * reset a torrent download and gathers statistics such as the download rate and the total
 * number of bytes downloaded. This class is a singleton, so only one instance per VM is allowed.
 * 
 * All settings and files required by the Azureus engine will be written to the 
 * directory <USER_HOME>/client-torrent-manager/az-config.
 */
public class ClientTorrentManager implements DownloadManagerTrackerListener{
	
	/**
	 * The relative path on the server where the torrent of the latest data file is kept.
	 */
	public static final String TORRENT_PATH = "/torrent-server/download.torrent";
	
	/**
	 * Singleton instance of this class
	 */
	private static ClientTorrentManager trClient;
	
	/**
	 * A convenient locator to the Azureus Core.
	 */
	private AzureusCore core;
	private DownloadManager fileDownloadManager;
	
	private File torrentFile;
	private File destination;
	
	private String server;
	private URL torrentFileURL;
	
	private boolean running; 
	
	/**
	 * A list of message that might be useful to calling clients.
	 */
	private Stack<String> messages;
	
	/**
	 * A list of all the clients that want to be notified when the 
	 * tracker's torrent gets updated.
	 */
	private List<WeakReference<TorrentFileListener>> torrentChangeListeners;
	
	/**
	 * Gets the singleton instance of the class. If none exists, a new one is created.
	 * 
	 * @return the singleton instance
	 */
	public static synchronized ClientTorrentManager getInstance(){
		if(trClient == null){
			
			String workDirectory = (System.getProperty("user.home") + File.separator + 
							   		"client-torrent-manager" + File.separator + 
							   		"az-config");
			
			System.setProperty("azureus.config.path", workDirectory);
			trClient = new ClientTorrentManager();
		}
		
		return trClient;
	}
	
	/**
	 * Checks to see whether the manager is currently running.
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Adds a new <code>TorrentFileListener</code> instance to the list of
	 * client that will be notified when it is detected the torrent on the
	 * tracker server has changed. If the instance is already on the list, 
	 * no action is taken.
	 * 
	 * @param listener - the listener instance to add to the list
	 */
	public void addTorrentChangeListener(TorrentFileListener listener){
		for(WeakReference<TorrentFileListener> ref : torrentChangeListeners){
			if(ref.get() != null && ref.get() == listener){
				return;
			}
		}
		torrentChangeListeners.add(new WeakReference<TorrentFileListener>(listener));
	}
	/**
	 * Removes <code>TorrentFileListener</code> instance from the list of
	 * client that will be notified when it is detected the torrent on the
	 * tracker server has changed.
	 * 
	 * @param listener - the listener instance to remove.
	 */
	public void removeTorrentChangeListener(TorrentFileListener listener){
		for(WeakReference<TorrentFileListener> ref : torrentChangeListeners){
			if(ref.get() != null && ref.get() == listener){
				torrentChangeListeners.remove(ref);
			}
		}
	}

	/**
	 * Method defined by the <code>DownloadManagerTrackerListener</code> interface. Of no
	 * real use in this class.
	 */
	public void announceResult(TRTrackerAnnouncerResponse response) {
		//Not interested in these events.
	}

	/**
	 * Method defined by the <code>DownloadManagerTrackerListener</code> interface
	 * - inside this method we determine whether the torrent on the tracker has changed.
	 */
	public void scrapeResult(TRTrackerScraperResponse response) {
		addMessage("Tracker Scrape Result : " + response.getStatusString());
		if(response.getStatus() == TRTrackerScraperResponse.ST_ERROR){
			if(response.getStatusString().indexOf("File Not Found") != -1){
				//We need to notify all the listeners
				for(WeakReference<TorrentFileListener> ref : torrentChangeListeners){
					ref.get().trackerTorrentFileChanged(new TorrentFileEvent(this));
				}
			}
		}
	}
	
	/**
	 * Private constructor to prevent direct instantiation.
	 */
	private ClientTorrentManager(){
		messages = new Stack<String>();
		torrentChangeListeners = new ArrayList<WeakReference<TorrentFileListener>>();
		core = AzureusCoreFactory.create();
		core.start();
		reset();
	}
	

	/**
	 * Reset the manager. It basically stops and removes downloads / seeds.
	 */
	@SuppressWarnings("unchecked")
	public void reset(){
		messages.clear();
		torrentFile = null;
		stopTorrentProcess();
		List<DownloadManager> downloadManagers = core.getGlobalManager().getDownloadManagers();
		for(DownloadManager manager : downloadManagers){
			try {
				core.getGlobalManager().removeDownloadManager(manager);
			} catch (Exception e) {}
		}
	}
	
	/**
	 * Compute the full URL of the torrent file based on the server name and
	 * <code>TORRENT_PATH</code>
	 * 
	 * @return the full torrent path.
	 */
	private URL getTorrentFileURL(){
		if(server == null) return null;
		
		if(torrentFileURL == null){
			try{
				torrentFileURL = new URL("http://" + server + TORRENT_PATH);
			}catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		return torrentFileURL;
	}
	
	/**
	 * Starts the torrent process. The torrent process will compute the 
	 * full URL to the torrent on the server, download a new copy 
	 * and start the process. If an existing file with the same name but  a different size is
	 * found in the download location, it will be overwritten.
	 * 
	 * @return true if the server was successfully start, otherwise false to indicate an error has occured.
	 * @throws IllegalStateException - the the destination path is empty, does not exist or if the server has not been specified.
	 */
	public boolean startTorrentProcess() throws IllegalStateException{
		
		if(server == null) throw new IllegalStateException("Please set a server name prior to calling this method.");
		if(destination == null) throw new IllegalStateException("Please set a destination directory prior to calling this method.");
		if(destination.exists() == false ||
		   destination.isDirectory() == false) 
			throw new IllegalStateException("Please set a valid destination directory prior to calling this method.");
 
		if(torrentFile == null){
			//We need to download the torrentFile 
			boolean success = downloadTorrentFile();
			if(success == false){
				addMessage("Could not download torrent file - please check that you have specified the right server and that it is running.");
				running = false;
				return false;
			}else{
				addMessage("Torrent file downloaded from server.");
			}
		}
		
		//Now that we have the torrent file, we can fire up the manager and start downloading...
		GlobalManager globalManager = core.getGlobalManager();
		fileDownloadManager = globalManager.addDownloadManager(torrentFile.getAbsolutePath(), destination.getAbsolutePath());
		fileDownloadManager.setForceStart(true);
		File saveLocation = fileDownloadManager.getSaveLocation();
		if(saveLocation.exists() == true){
			//This file already exists on disk
			//attempt to delete it if the size does not match the torrent's
			long torrentFileSize = fileDownloadManager.getSize();
			long actualFileSize = saveLocation.length();
			if(torrentFileSize != actualFileSize){
				addMessage("Length of torrent file (" + torrentFileSize + ") and file already on disk (" + actualFileSize + ") does not match. " +	
						   "It will be overwritten.");
				saveLocation.delete();
			}
		}
		
		fileDownloadManager.addTrackerListener(this);
		fileDownloadManager.addListener(new DownloadManagerListener(){
		
			public void completionChanged(DownloadManager manager, boolean bCompleted) {}
			public void downloadComplete(DownloadManager manager) {
				addMessage("File Successfully Downloaded...");
			}
			public void filePriorityChanged(DownloadManager download, DiskManagerFileInfo file) {}
			public void positionChanged(DownloadManager download, int oldPosition, int newPosition) {}

			public void stateChanged(DownloadManager manager, int state) {
				
				switch(state){
					case DownloadManager.STATE_ALLOCATING:
						addMessage("Torrent Allocating...");
					break;
					case DownloadManager.STATE_CHECKING:
						addMessage("Torrent Checking...");
					break;
					case DownloadManager.STATE_DOWNLOADING:
						addMessage("Torrent Downloading...");
					break;
					case DownloadManager.STATE_WAITING:
						addMessage("Torrent Waiting...");
					break;
					case DownloadManager.STATE_QUEUED:
						addMessage("Torrent Queued...");
					break;
					case DownloadManager.STATE_INITIALIZED:
						addMessage("Torrent Initialised...");
					break;
					case DownloadManager.STATE_INITIALIZING:
						addMessage("Torrent Initialising...");
					break;
					case DownloadManager.STATE_ERROR:
						addMessage("Torrent Error Encountered... - " + manager.getErrorDetails());
					break;
					case DownloadManager.STATE_CLOSED:
						addMessage("Torrent Closed...");
					break;
				}
				
			}
				
		});
		globalManager.startAllDownloads();
		
		addMessage("Started downloading torrent file....");

		running = true;
		return true;
	}
	
	/**
	 * Stops the torrent process. 
	 * 
	 * @return - true if the server was successfully stopped - otherwise false.
	 */
	public boolean stopTorrentProcess(){
		GlobalManager globalManager = core.getGlobalManager();
		globalManager.stopAllDownloads();
		running = false;
		return true;
	}
	
	public File getDestination() {
		return destination;
	}

	public void setDestination(File destination) {
		this.destination = destination;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	/**
	 * Checks to see if the manager is currently busy downloading the 
	 * torrent file
	 * @return true if the torrent file is currently downloading, otherwise false;
	 */
	public boolean isDownloading(){
		if(fileDownloadManager != null){
			return fileDownloadManager.getState() == DownloadManager.STATE_DOWNLOADING;
		}else{
			return false;
		}
	}
	
	/**
	 * Checks to see if the manager has completed the download if the torrent data file 
	 * and is currently seeding it to the rest of the peers.
	 * 
	 * @return - true if the data file has finished downloading and is being seeded to other peers. Otherwise false
	 */
	public boolean isSeeding(){
		if(fileDownloadManager != null){
			return fileDownloadManager.getState() == DownloadManager.STATE_SEEDING;
		}else{
			return false;
		}
	}
	
	/**
	 * Gets the percentage points (out of a hundred) of how much of the 
	 * torrent data file has been downloaded.
	 * @return the percentage completed - i.e. 65.3
	 */
	public double getPercentDone(){
		if(fileDownloadManager != null){
			return fileDownloadManager.getStats().getCompleted() / 10.0;
		}else{
			return -1;
		}
	}

	/**
	 * Returns the total number of bytes that has been uploaded by the
	 * <code>ClientTorrentManager</code> instance for the current torrent
	 * file.
	 * 
	 * @return the number of bytes uploaded to other peers. -1 if the manager is not running
	 */
	public double getTotalBytesUploaded(){
		if(fileDownloadManager != null){
			return fileDownloadManager.getStats().getTotalDataBytesSent();
		}else{
			return -1;
		}
	}
	
	/**
	 * Returns the total number of bytes that has been downloaded by the
	 * <code>ClientTorrentManager</code> instance for the current torrent
	 * file.
	 * 
	 * @return the number of bytes download from other peers. -1 if the manager is not running
	 */
	public double getTotalBytesDownloaded(){
		if(fileDownloadManager != null){
			return fileDownloadManager.getStats().getTotalDataBytesReceived();
		}else{
			return -1;
		}
	}
	
	/**
	 * Returns the current rate at which data is being uploaded to other peers on the 
	 * network for the current torrent by this <code>ClientTorrentManager</code> instance
	 * 
	 * @return the rate in bytes per second uploaded to peers. -1 if the manager is not running
	 */
	public double getDataSendRate(){
		if(fileDownloadManager != null){
			return fileDownloadManager.getStats().getDataSendRate();
		}else{
			return -1;
		}
	}
	
	/**
	 * Returns the current rate at which data is being downloaded from other peers on the 
	 * network for the current torrent by this <code>ClientTorrentManager</code> instance.
	 * 
	 * @return the rate in bytes per second downloaded from other peers. -1 if the manager is not running
	 */
	public double getDataReceiveRate(){
		if(fileDownloadManager != null){
			return fileDownloadManager.getStats().getDataReceiveRate();
		}else{
			return -1;
		}
	}
	 
	/**
	 * Stops this instance and the underlying Azureus engine.
	 */
	public void close(){
		stopTorrentProcess();
		core.requestStop();
	}
	
	private void addMessage(String message){
		messages.push(message);
	}
	
	/**
	 * Gets and clears the message backlog generated by this <code>ClientTorrentManager</code> 
	 * instance.
	 * 
	 * @return - the current list of messages
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getMessages(){
		Collection<String> copy = (Collection<String>)(messages.clone());
		messages.clear();
		return copy;
	}
	
	/**
	 * A utility method used to download the torrent file from the 
	 * tracker / torrent file hosting server.
	 * 
	 * @return true if the file was successfully downloaded - otherwise false.
	 */
	private boolean downloadTorrentFile(){
		
		URL torrentFileURL = getTorrentFileURL();
		InputStream ins = null; 

		OutputStream ous = null;
		BufferedOutputStream bufOus = null;
		File downloadedTorrentFile = null; 
		
		try{
			downloadedTorrentFile = File.createTempFile("torrentDownload", "torrent");
			ins = torrentFileURL.openStream();
			byte[] buffer = new byte[4096];
			
			ous = new FileOutputStream(downloadedTorrentFile);
			bufOus = new BufferedOutputStream(ous);
			
			int rd = 0;
			
			while((rd = ins.read(buffer)) != -1){
				bufOus.write(buffer,0,rd);
			}
			
			torrentFile = downloadedTorrentFile;
			addMessage("Successfully downloaded torrent file - saved as " + torrentFile.getName());
			
		}catch(IOException e){
			e.printStackTrace();
			torrentFile = null;
			return false;
			
		}finally{
			try {
				if(ins != null) ins.close();
				if(bufOus != null) bufOus.close();
				if(ous != null) ous.close();
			} catch (IOException e) {
				e.printStackTrace();
				torrentFile = downloadedTorrentFile;
				return false;
			}
		}
		return true;
	}
	
}
















