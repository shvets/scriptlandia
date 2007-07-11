package client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.gudy.azureus2.core3.disk.DiskManagerFileInfo;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.download.DownloadManagerListener;
import org.gudy.azureus2.core3.global.GlobalManager;

import com.aelitis.azureus.core.AzureusCore;
import com.aelitis.azureus.core.AzureusCoreException;
import com.aelitis.azureus.core.AzureusCoreFactory;

/**
 * A simple standalone class / application that
 * can be used to download the file specified by a torrent file.
 * 
 * The URL of the torrent file is passed in as a command line parameter.
 */
public class SimpleStandaloneDownloader {

	/**
	 * A reference to the Azureus engine used to download the file
	 */
	private static AzureusCore core;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
		
		//Set the default root directory for the azureus engine.
		//If not set, it defaults to the user's home directory.
		System.setProperty("azureus.config.path", "run-environment/az-config");
		
		String url = null;
		
		if(args.length != 1){
			System.out.println("You have not specified a parameter, so the system will default to the azureus JAR torrent");
			url = "http://ovh.dl.sourceforge.net/sourceforge/azureus/Azureus2.5.0.2.jar.torrent";
		}else{
			url = args[0];
		}
 
		core = AzureusCoreFactory.create();
		core.start();
		
		//Clear out any previously saved download managerst there might be.
		List<DownloadManager> downloadManagers = core.getGlobalManager().getDownloadManagers();
		for(DownloadManager manager : downloadManagers){
			try {
				core.getGlobalManager().removeDownloadManager(manager);
			} catch (Exception e) { /* Nothing we can really do here */}
		}
		
		System.out.println("Attempting to download torrent at : " + url);
		
		File downloadedTorrentFile = downloadTorrentFile(new URL(url));
		
		System.out.println("Completed download of : " + url);
		System.out.println("File stored as : " + downloadedTorrentFile.getAbsolutePath());
		
		File downloadDirectory = new File("downloads");
		if(downloadDirectory.exists() == false) downloadDirectory.mkdir();
		
		//Start the download of the torrent 
		GlobalManager globalManager = core.getGlobalManager();
		DownloadManager manager = globalManager.addDownloadManager(downloadedTorrentFile.getAbsolutePath(),
																   downloadDirectory.getAbsolutePath());
		
		
		DownloadManagerListener listener = new DownloadStateListener();
		manager.addListener(listener);		
		globalManager.startAllDownloads();
		
	}
	
	/**
	 * A listener class that will print out information to the system console
	 * on the current status of the torrent data file download.
	 */
	private static class DownloadStateListener implements DownloadManagerListener{

		public void stateChanged(DownloadManager manager, int state) {
			switch(state){
				case DownloadManager.STATE_CHECKING :
					System.out.println("Checking....");
				break;
				case DownloadManager.STATE_DOWNLOADING :
					System.out.println("Downloading....");
					
					//Start a new daemon thread periodically check 
					//the progress of the upload and print it out 
					//to the command line
					Runnable checkAndPrintProgress = new Runnable(){
						@SuppressWarnings("unchecked")
						public void run(){
							try{
								boolean downloadCompleted = false;
								while(!downloadCompleted){
									AzureusCore core = AzureusCoreFactory.getSingleton();
									List<DownloadManager> managers = core.getGlobalManager().getDownloadManagers();

									//There is only one in the queue.
									DownloadManager man = managers.get(0);
									System.out.println("Download is " + 
													   (man.getStats().getCompleted() / 10.0) + 
													   " % complete");
									downloadCompleted = man.isDownloadComplete(true);
									//Check every 10 seconds on the progress
									Thread.sleep(10000);
								}

							}catch(Exception e){
								throw new RuntimeException(e);
							}

						}
					};
					
					Thread progressChecker = new Thread(checkAndPrintProgress);
					progressChecker.setDaemon(true);
					progressChecker.start();
					 
				break;
				case DownloadManager.STATE_FINISHING :
					System.out.println("Finishing Download....");
				break;
				case DownloadManager.STATE_SEEDING:
					System.out.println("Download Complete - Seeding for other users....");
				break;
				case DownloadManager.STATE_STOPPED:
					System.out.println("Download Stopped.");
				break;
			}
		}
		
		public void downloadComplete(DownloadManager manager) {
			System.out.println("Download Completed - Exiting.....");
			AzureusCore core = AzureusCoreFactory.getSingleton();
			try{
				core.requestStop();
			}catch(AzureusCoreException aze){
				System.out.println("Could not end Azureus session gracefully - " +
								   "forcing exit.....");
				core.stop();
			}
		}
		
		public void filePriorityChanged(DownloadManager download, DiskManagerFileInfo file) {}
		public void positionChanged(DownloadManager download, int oldPosition, int newPosition) {}
		public void completionChanged(DownloadManager manager, boolean bCompleted) {}
	}

	
	/**
	 * A utility method used to download the torrent file from the 
	 * tracker / torrent file hosting server.
	 * 
	 * @return the downloaded torrent file. This is a temporary file.
	 */
	private static File downloadTorrentFile(URL torrentFileURL){
		
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
			
		}catch(IOException e){
			throw new RuntimeException("Could not download torrent file", e);
		}finally{
			try {
				if(ins != null) ins.close();
				if(bufOus != null) bufOus.close();
				if(ous != null) ous.close();
			} catch (IOException e) {
				throw new RuntimeException("Could not download torrent file", e);
			}
		}
		return downloadedTorrentFile;
	}
}

















