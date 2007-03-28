package view;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;


/**
 * @author janm
 */
public class PlainTextView implements View {

  public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/plain");
    response.addHeader("Content-disposition", "attachment; filename=output.txt");

    PrintWriter writer = response.getWriter();
    for (Iterator k = model.keySet().iterator(); k.hasNext();) {
      Object key = k.next();
      writer.print(key);
      writer.println(" contains:");
      writer.println(model.get(key));
    }
  }

  public String getContentType() {
    return "text/plain";
  }

}
