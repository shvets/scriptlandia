package server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.gudy.azureus2.core3.torrent.TOTorrentException;
import org.gudy.azureus2.core3.tracker.host.TRHostException;
import org.gudy.azureus2.core3.tracker.host.TRHostTorrentRemovalVetoException;


/**
 * New files to be hosted on the tracker server is uploaded using this servlet.
 * Internally it relies on the Jakarta Commons File Upload library to 
 * parse the incoming multi-part request.
 *
 */
public class NewTorrentFileUploadServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		hostNewTorrent(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		hostNewTorrent(request,response);
	}
 
	@SuppressWarnings("unchecked")
	private void hostNewTorrent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			RequestContext requestContext = new ServletRequestContext(request);
			boolean isMultipart = ServletFileUpload.isMultipartContent(requestContext);
 
			if(isMultipart == false){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
 
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(0);
			ServletFileUpload uploadParser = new ServletFileUpload(factory);
			List<FileItem> fileItems = uploadParser.parseRequest(requestContext);
			
			if(fileItems.isEmpty()){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			String specifiedHostname = null;
			
			DiskFileItem fileUploadItem = null;
			for(FileItem itm : fileItems){
				if(itm.isFormField() == false){
					fileUploadItem = (DiskFileItem)itm;
				}else{
					if(itm.getFieldName().equals("externalIPAddress")){
						specifiedHostname = itm.getString();
					}
				}
			}
			
			File sourceFile = fileUploadItem.getStoreLocation(); 
			ServletContext context = getServletContext();
			TorrentSeederTrackerServer server = (TorrentSeederTrackerServer)context.getAttribute(ServerInitialiser.TORRENT_SERVER_ATTRIBUTE);
			
			if(server.getHostname().equals(specifiedHostname) == false){
				server.setHostname(specifiedHostname);
			}
			
			server.seedFile(sourceFile);
			
			RequestDispatcher dispatcher = context.getRequestDispatcher("/upload-success.jsp");
			dispatcher.forward(request, response);
			
		} catch (FileUploadException e) {
			throw new ServletException("Could not upload / host file " + e);
		} catch (TRHostTorrentRemovalVetoException e) {
			throw new ServletException("Could not upload / host file " + e);
		} catch (TOTorrentException e) {
			throw new ServletException("Could not upload / host file " + e);
		} catch (TRHostException e) {
			throw new ServletException("Could not upload / host file " + e);
		}
		
	}
	
	
}





