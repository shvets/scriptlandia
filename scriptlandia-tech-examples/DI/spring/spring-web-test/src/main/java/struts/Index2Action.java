package struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.struts.ActionSupport;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import data.ItemManager;

/**
 * @author janm
 */
public class Index2Action extends ActionSupport {

  private ItemManager itemManager;

  protected void onInit() {
    itemManager = (ItemManager) getWebApplicationContext().getBean("itemManager");
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("items", itemManager.getItems());
    request.setAttribute("fromAction", "Index2Action");

    return mapping.findForward("success");
  }

}
