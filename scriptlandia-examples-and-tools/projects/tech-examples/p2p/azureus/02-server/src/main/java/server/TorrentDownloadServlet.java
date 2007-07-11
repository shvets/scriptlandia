package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a simple servlet that will make the latest .torrent file available for download by 
 * clients. If the server has not yet started seeding a torrent, a 404 status will be returned.
 * 
 * @author J Steenkamp
 */
public class TorrentDownloadServlet extends HttpServlet{

	public static final String TORRENT_FILE_PATH = "/WEB-INF/azureus-base/data.torrent";
	private ServletContext context;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		context = config.getServletContext();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		InputStream torrentFile = context.getResourceAsStream(TORRENT_FILE_PATH);
		if(torrentFile == null){
			//Not Found 
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		response.setContentType("application/x-bittorrent");
		OutputStream ous = response.getOutputStream();
		
		byte[] buffer = new byte[2048];
		int bytesRead = 0;
		while((bytesRead = torrentFile.read(buffer)) != -1){
			ous.write(buffer, 0, bytesRead);
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	
	
	
}