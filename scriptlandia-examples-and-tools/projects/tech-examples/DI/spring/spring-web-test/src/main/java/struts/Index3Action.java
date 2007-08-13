package struts;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import data.ItemManager;

/**
 * @author janm
 */
public class Index3Action extends Action {

  private ItemManager itemManager;

  public void setServlet(ActionServlet servlet) {
    super.setServlet(servlet);

    ServletContext application = servlet.getServletContext();

    WebApplicationContext webApplicationContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(application);

    itemManager = (ItemManager) webApplicationContext.getBean("itemManager");
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("fromAction", "Index3Action");

    return mapping.findForward("success");
  }

}
