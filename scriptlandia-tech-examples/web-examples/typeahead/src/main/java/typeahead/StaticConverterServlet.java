package typeahead;

import java.io.IOException;
import java.util.List;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import org.apache.struts.util.LabelValueBean;

/**
 * IRIS Static converter Servlet.
 * Genetares javascript code for supporting typeahead control.
 *
 * @author Alexander Shvets
 * @version 1.0
 */
public class StaticConverterServlet extends HttpServlet {

  /**
   * @see HttpServlet
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
    doIt(request, response);
  }

  /**
   * @see HttpServlet
   */
  public void doGet (HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
    doIt(request, response);
  }

  /**
   *  Prepares javascript.
   *
   * @param request the request
   * @param response the response
   * @throws ServletException the servlet exception
   * @throws IOException the I/O exception
   */
  public void doIt(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
    response.setContentType("text/javascript");

    ServletOutputStream out = response.getOutputStream();

    generateJavascript(out, request);

    out.close();
  }

  /**
   * Generates javascript code.
   * @param out servlet output stream
   * @param request the request
   * @throws IOException I/O exception
   */
  private void generateJavascript(ServletOutputStream out, HttpServletRequest request)
          throws IOException {
    String listName = request.getParameter("listName");
    String autosuggestId = request.getParameter("autosuggestId");

    HttpSession session = request.getSession();

    Object listObject = session.getAttribute(listName);
    session.removeAttribute(listName);

    out.println("function " + autosuggestId + "_convertList() {");

    out.println("  var items = [");

    if(listObject instanceof List) {
      List list = (List)listObject;

      Iterator iterator = list.iterator();

      while (iterator.hasNext()) {
        Object object = iterator.next();

        String key = null;
        String value = null;

        if(object instanceof LabelValueBean) {
          LabelValueBean bean = (LabelValueBean)object;
          key = bean.getValue();
          value = bean.getLabel();
        }
        else if(object instanceof Pair) {
          Pair pair = (Pair)object;
          key = pair.getKey();
          value = pair.getContent();
        }
        else if(object instanceof String) {
          String bean = (String)object;

          int index = bean.indexOf(":");
          key = bean.substring(0, index);
          value = bean.substring(index+1);
        }

        if(key != null) {
          out.print("    new AutoSuggestPair('" +  key + "',");

          if(value.indexOf("\'") == -1) {
            out.print("'" + value + "'");
          }
          else {
            out.print("\"" + value + "\"");
          }

          out.print(")");

          if(iterator.hasNext()) {
            out.println(",");
          }
        }
      }
    }

    out.println();
    out.println("  ];");

    out.println("  return items;");
    out.println("}");
  }

  /**
   * @see HttpServlet
   */
  public String getServletInfo() {
    return "A static converter servlet";
  }

}
