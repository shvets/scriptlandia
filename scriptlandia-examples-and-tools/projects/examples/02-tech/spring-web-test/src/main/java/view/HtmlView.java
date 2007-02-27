package view;

import java.util.Map;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @author janm
 */
public class HtmlView implements View {

  public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html");

    PrintWriter writer = response.getWriter();

    writer.write("Hello from html view");

/*    UrlPathHelper urlPathHelper = new UrlPathHelper();
    //urlPathHelper.setAlwaysUseFullPath(true);
    String urlPath = urlPathHelper.getLookupPathForRequest(request);

    URL url = new URL("http", request.getServerName(), urlPath);
    URLConnection urlConnection = url.openConnection();

    BufferedInputStream is = null;

    try {
      is = new BufferedInputStream(urlConnection.getInputStream());
      OutputStream os = response.getOutputStream();

      final byte[] buffer = new byte[2048];

      boolean ok = false;

      while (!ok) {
        final int n = is.read(buffer);

        if (n == -1) {
          ok = true;
        } else {
          os.write(buffer, 0, n);
        }
      }
    }
    finally {
      if(is != null) {
        is.close();
      }
    }
*/

  }

}
