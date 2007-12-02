package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.gudy.azureus2.core3.config.COConfigurationManager;
import org.gudy.azureus2.core3.disk.DiskManagerFileInfo;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.download.DownloadManagerListener;
import org.gudy.azureus2.core3.download.DownloadManagerStats;
import org.gudy.azureus2.core3.global.GlobalManager;
import org.gudy.azureus2.core3.global.GlobalManagerDownloadRemovalVetoException;
import org.gudy.azureus2.core3.torrent.TOTorrent;
import org.gudy.azureus2.core3.torrent.TOTorrentCreator;
import org.gudy.azureus2.core3.torrent.TOTorrentException;
import org.gudy.azureus2.core3.torrent.TOTorrentFactory;
import org.gudy.azureus2.core3.tracker.host.TRHost;
import org.gudy.azureus2.core3.tracker.host.TRHostException;
import org.gudy.azureus2.core3.tracker.host.TRHostTorrent;
import org.gudy.azureus2.core3.tracker.host.TRHostTorrentRemovalVetoException;
import org.gudy.azureus2.core3.util.TorrentUtils;

import com.aelitis.azureus.core.AzureusCore;
import com.aelitis.azureus.core.AzureusCoreException;
import com.aelitis.azureus.core.AzureusCoreFactory;

/**
 * <code>TorrentSeederTrackerServer</code> is a singleton class that servers as the main 
 * 'engine' of the server application. It acts a facade around the underlying Azureus 
 * engine and makes it possible to create a new torrent file, start a HTTP tracker server and
 * an initial seeder all in one method invocation.
 * 
 * The name of the data file  (distributed-data.dat) and port at which the tracker server (6671) runs
 * cannot be amended. Future version of this class might make this possible. The external hostname of 
 * the server can however be changed. 
 *   
 */
public class TorrentSeederTrackerServer {
	
	/**
	 * The constant name of the data file
	 */
	public static final String DATA_FILE_NAME = "distributed-data.dat";
	/**
	 * The port number of the tracker.
	 */
	public static final int TRACKER_PORT_NUMBER = 6671; 
	
	/**
	 * A reference to the Azureus engine.
	 */
	private AzureusCore core;
	
	/**
	 * The singleton instance for this class.
	 */
	private static TorrentSeederTrackerServer instance;

	/**
	 * The root directory which this server will use to store all the files
	 * and settings required by the Azureus engine.
	 */
	private File serverRoot;
	
	/**
	 * The file to distribute over BitTorrent
	 */
	private File sourceFile;
	private boolean running;

	/**
	 * The current torrent being hosted.
	 */
	private TRHostTorrent currentTorrent;
	
	private long maxDataSendRate;
	private String lastStatus;
	
	/**
	 * Returns the singleton instance of this class. Please note that this method can only be called after 
	 * getNewInstance() has been invoked.
	 * 
	 * @return the singleton instance
	 * @throws IllegalStateException - If the getNewInstance has not yet been called.
	 */
	public static TorrentSeederTrackerServer getInstance() throws IllegalStateException{
		if(instance == null){
			throw new IllegalStateException("This server has not yet been started - please call the getNewInstance() method to start up");
		}
		return instance;
	}
	
	/**
	 * Creates and returns the singleton instance of this class. This method is required because we
	 * need to set the location of where the Azureus engine will create all its settings and required
	 * files.
	 *  
	 * @param serverRoot - the directory where the Azureus engine can store its settings / files
	 * @return the singleton instance 
	 * @throws IllegalStateException if a singleton of this class has already been created.
	 */
	public synchronized static TorrentSeederTrackerServer getNewInstance(File serverRoot) throws IllegalStateException{
		if(instance != null){
			throw new IllegalStateException("An instance of the server has already been created.");
		}
		System.setProperty("azureus.config.path", serverRoot.getAbsolutePath() + File.separator + "az-config");
		instance = new TorrentSeederTrackerServer(serverRoot);
		return instance;
	}
	
	/**
	 * Sets the hostname to be used by the tracker server. This value
	 * is important as all generated torrent files will contain this value. 
	 * 
	 * @param hostname the new external hostname
	 */
	public void setHostname(String hostname){
		COConfigurationManager.setParameter("Tracker IP",hostname);
		COConfigurationManager.save();
	}
	
	/**
	 * @return The current hostname
	 */
	public String getHostname(){
		return COConfigurationManager.getStringParameter("Tracker IP");
	}
	
	/**
	 * The constructor is private to prevent direct instantiation.
	 */
	private TorrentSeederTrackerServer(File serverRoot){
		this.serverRoot = serverRoot;
		
		COConfigurationManager.preInitialise();
		COConfigurationManager.setParameter("Tracker Port Enable",true);
		COConfigurationManager.setParameter("Sharing Protocol","HTTP");
		COConfigurationManager.setParameter("update.start",false);
		COConfigurationManager.setParameter("Auto Update",false);
		
		String hostname = COConfigurationManager.getStringParameter("Tracker IP");
		if(hostname == null || hostname.trim().equals("")){
			setHostname("127.0.0.1");
		}
		COConfigurationManager.save();
		
		core = AzureusCoreFactory.create();
		core.start();
	}
	
	/**
	 * Checks to see whether the current tracker server and seeder is running.
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	public File getSourceFile() {
		return sourceFile;
	}
	
	/**
	 * Returns the last status of the torrent file seeder. 
	 * @return
	 */
	public String getLastStatus(){
		return lastStatus;
	}
	
	/**
	 * Stops the tracker server and the torrent seeder. It also halts the
	 * Azureus engine.
	 */
	public void stopServer() {
		stopTorrentAndSeeder();
		try {
			core.requestStop();
		} catch (AzureusCoreException e) {
			System.err.println("Could not stop AzureusCore - forcing shutdown.");
			core.stop();
		}
	}
	
	/**
	 * Stops the tracker server and the torrent seeder. However, it does not halt
	 * the Azureus engine.
	 */
	public void stopTorrentAndSeeder(){
		if(currentTorrent != null){
			try {

				currentTorrent.remove();
				GlobalManager glManager = core.getGlobalManager();
				DownloadManager seederManager = glManager.getDownloadManager(currentTorrent.getTorrent());
				if(seederManager != null){
					seederManager.destroy(false);
				}
				glManager.removeDownloadManager(seederManager);
				running = false;
				
			} catch (TRHostTorrentRemovalVetoException e) {
				throw new RuntimeException("Could not remove torrent ! - " + e);
			} catch (GlobalManagerDownloadRemovalVetoException e) {
				throw new RuntimeException("Could not remove torrent ! - " + e);
			}
		}
	}
	
	/**
	 * Starts hosting a new data file on the tracker server and starts a new initial seeded. 
	 * All previously hosted torrents are removed from the tracker server.  
	 * 
	 * @param upload the new file to create a torrent file for and to track on the tracker server.
	 * @throws TRHostTorrentRemovalVetoException if an error has occured in the underlying Azureus engine
	 * @throws TOTorrentException if an error has occured in the underlying Azureus engine
	 * @throws TRHostException if an error has occured in the underlying Azureus engine
	 */
	public void seedFile(File upload) throws TRHostTorrentRemovalVetoException, TOTorrentException, TRHostException{
		
		//Move the source file to a location within our torrent directory.
		sourceFile = new File(serverRoot,DATA_FILE_NAME); 
		
		try{
			copy(upload,sourceFile);
		}catch(IOException e){
			throw new RuntimeException("Could not host file - could not move temporary file to new location");
		}
		
		TRHost trHost = core.getTrackerHost();
		stopTorrentAndSeeder();
		TOTorrentCreator torrentCreator = TOTorrentFactory.createFromFileOrDirWithComputedPieceLength(sourceFile, getTrackerURL());
		TOTorrent torrent = torrentCreator.create();
		currentTorrent = trHost.hostTorrent(torrent, true, false);
		
		//However, we also need to implement a seeder that will be responsible for
		//'giving' the initial file to the network.
		File bEncodedFile = new File(serverRoot + File.separator + "data.torrent");
		TorrentUtils.writeToFile(torrent,bEncodedFile);
		
		GlobalManager glManager = core.getGlobalManager();
		DownloadManager seederManager = glManager.addDownloadManager(bEncodedFile.getAbsolutePath(), serverRoot.getAbsolutePath());
		seederManager.setForceStart(true);
		seederManager.addListener(new DownloadManagerListener(){

			public void completionChanged(DownloadManager manager, boolean bCompleted) {}
			public void downloadComplete(DownloadManager manager) {}
			public void filePriorityChanged(DownloadManager download, DiskManagerFileInfo file) {}
			public void positionChanged(DownloadManager download, int oldPosition, int newPosition) {}

			public void stateChanged(DownloadManager manager, int state) {
				
				switch(state){
					case DownloadManager.STATE_ALLOCATING:
						lastStatus = "Torrent Allocating...";
					break;
					case DownloadManager.STATE_CHECKING:
						lastStatus = "Torrent Checking...";
					break;
					case DownloadManager.STATE_DOWNLOADING:
						lastStatus = "Torrent Downloading...";
					break;
					case DownloadManager.STATE_WAITING:
						lastStatus = "Torrent Waiting...";
					break;
					case DownloadManager.STATE_QUEUED:
						lastStatus = "Torrent Queued...";
					break;
					case DownloadManager.STATE_INITIALIZED:
						lastStatus = "Torrent Initialised...";
					break;
					case DownloadManager.STATE_INITIALIZING:
						lastStatus = "Torrent Initialising...";
					break;
					case DownloadManager.STATE_ERROR:
						lastStatus = "Torrent Error Encountered... " + manager.getErrorDetails();
					break;
					case DownloadManager.STATE_SEEDING:
						lastStatus = "Torrent Seeding... ";
					break;
					case DownloadManager.STATE_CLOSED:
						lastStatus = "Torrent Closed...";
					break;
				}
				
			}
				
		});
		
		glManager.startAllDownloads();
		running = true;
	}
	
	/**
	 * Get the current torrent being tracked by the tracker server.
	 * 
	 * @return the current torrent file
	 */
	public TRHostTorrent getCurrentTorrent() {
		return currentTorrent;
	}

	/**
	 * Complies the URL of the tracker using the port number and external hostname 
	 * 
	 * @return the HTTP URL that can be used by torrent clients to access the tracker.
	 */
	private URL getTrackerURL(){
		String hostname = getHostname();
		try {
			URL trackerURL = new URL("http",hostname,TRACKER_PORT_NUMBER,"/tracker");
			return trackerURL;
		} catch (MalformedURLException e) {
			throw new RuntimeException("Unable to create Tracker URL for port : " + TRACKER_PORT_NUMBER + " and hostname : " + hostname);
		}
	}

	/**
	 * Returns the port number of this tracker.
	 * 
	 * @return
	 */
	public int getTrackerPortNumber() {
		return TRACKER_PORT_NUMBER;
	}
	
	/**
	 * Gets the number of time the current torrent has been successfully downloaded in full
	 * @return the completed number of downloads
	 */
	public long getCompletedCount(){
		return (currentTorrent != null) ? currentTorrent.getCompletedCount() : 0;
	}
	
	/**
	 * The total number of bytes that have been downloaded for the current torrent 
	 * @return total number of downloaded bytes.
	 */
	public long getTotalDownloaded(){
		return (currentTorrent != null) ? currentTorrent.getTotalDownloaded() : 0;
	}
	
	/**
	 * The total number of peers tracked by this tracker that have not yet completed the download
	 * of the current torrent file.
	 * @return the number peers with incomplete downloads.
	 */
	public int getLeecherCount(){
		return (currentTorrent != null) ? currentTorrent.getLeecherCount() : 0;
	}
	
	/**
	 * The total number of peers connected to the tracker server.
	 * @return number of connected peers.
	 */
	public int getPeerCount(){
		return (currentTorrent != null) ? currentTorrent.getSeedCount() : 0;
	}
	
	/**
	 * The rate at which data is being downloaded from the seeder.
	 * @return data download rate in bytes per second.
	 */
	public long getDataSendRate(){
		DownloadManagerStats stats = getSeederStats();
		
		if(stats != null){
			long sendRate = stats.getDataSendRate();
			if(sendRate > maxDataSendRate){
				maxDataSendRate = sendRate;
			}
			return sendRate;
		}else{
			return 0;
		}
	}
	/**
	 * The size of the data file being seeded
	 * @return the data file size in bytes.
	 */
	public long getSeedFileSize(){
		DownloadManager seederManager = getSeederManager();
		return (seederManager != null) ? seederManager.getSize() : 0;
	}
	
	/**
	 * The number of peers that is currently connected to the initial data file seeder
	 * @return number of connected peers.
	 */
	public int getNumberOfConnectedPeers(){
		DownloadManager seederManager = getSeederManager();
		return (seederManager != null) ? seederManager.getNbPeers() : 0;
	}
	
	/**
	 * The number of seeds that is currently connected to the initial data file seeder
	 * @return number of connected seeds.
	 */
	public int getNumberOfConnectedSeeds(){
		DownloadManager seederManager = getSeederManager();
		return (seederManager != null) ? seederManager.getNbSeeds() : 0;
	}
	
	/**
	 * Returns the number of pieces into which this file has been divided for 
	 * distribution over BitTorrent.
	 * 
	 * @return the number of file pieces
	 */
	public int getNumberOfPiecesForFile(){
		DownloadManager seederManager = getSeederManager();
		return (seederManager != null) ? seederManager.getNbPieces() : 0;
	}
	
	/**
	 * Returns the date and time when this torrent was created and first 
	 * tracked by the tracker server.
	 * 
	 * @return the creation time
	 */
	public Date getCreationTime(){
		DownloadManager seederManager = getSeederManager();
		return (seederManager != null) ? new Date(seederManager.getCreationTime()) : null;
	}
	
	/**
	 * An internal utility method to get the <code>DownloadManager</code> associated with
	 * the initial download seeder.
	 * 
	 * @return the <code>DownloadManager</code> associated with the initial torrent file seeder.
	 */
	private DownloadManager getSeederManager(){
		if(currentTorrent == null) return null;
		DownloadManager manager = core.getGlobalManager().getDownloadManager(currentTorrent.getTorrent());
		return manager;
	}
	
	/**
	 * The maximum rate at which data has been downloaded from the seeder for this torrent file.
	 * 
	 * @return peak data download rate in bytes per second.
	 */
	public long getMaxDataSendRate() {
		return maxDataSendRate;
	}
	
	/**
	 * An internal utility method to get the <code>DownloadManagerStats</code> associated with
	 * the initial download seeder.
	 * 
	 * @return the <code>DownloadManagerStats</code> associated with the initial torrent file seeder.
	 */
	private DownloadManagerStats getSeederStats(){
		if(currentTorrent == null) return null;
		DownloadManager manager = core.getGlobalManager().getDownloadManager(currentTorrent.getTorrent());
		if(manager != null){
			return manager.getStats();
		}else{
			return null;
		}
	}

	/**
	 * A small utility method to copy a file from one location to another
	 * @param src the source file
	 * @param dst the destination file
	 * @throws IOException if files could not be copied because of an I/O problem.
	 */
	private void copy(File src, File dst) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try{
			in = new FileInputStream(src);
			out = new FileOutputStream(dst);
			// Transfer bytes from in to out
			byte[] buf = new byte[8192];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		}finally{
			if(in != null) in.close();
			if(out != null) out.close();
		}

	}


}














