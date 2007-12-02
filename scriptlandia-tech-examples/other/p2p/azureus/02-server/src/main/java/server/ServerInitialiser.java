package server;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This class is responsible for starting the Azureus core engine and setting the 
 * path where it will store its settings. 
 * 
 * @author J Steenkamp
 */
public class ServerInitialiser implements ServletContextListener{

	public static final String TORRENT_SERVER_ATTRIBUTE = "torrentSeederTrackerServer";
	public static final String TORRENT_SERVER_BASE_PATH = "/WEB-INF/azureus-base";

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		File torrentBasePath = new File(context.getRealPath(TORRENT_SERVER_BASE_PATH));
		TorrentSeederTrackerServer server = TorrentSeederTrackerServer.getNewInstance(torrentBasePath);
		context.setAttribute(TORRENT_SERVER_ATTRIBUTE,server);
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		TorrentSeederTrackerServer server = (TorrentSeederTrackerServer)context.getAttribute(TORRENT_SERVER_ATTRIBUTE);
		server.stopServer();
	}

}