package struts2.action;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import business.User;
import business.UserService;

public class Struts2Action extends ActionSupport
  implements ModelDriven, Preparable, ServletRequestAware {

  private UserService userService/* = new UserServiceImpl()*/;

  private int userId;
  private User user;
  private HttpServletRequest request;

  public void setServletRequest(HttpServletRequest httpServletRequest) {
    this.request = httpServletRequest;
  }

  public void setId(int blogId) {
    this.userId = blogId;
  }

  /**
   * This method is executed before execute() method.
   * 
   * @throws Exception
   */
  public void prepare() throws Exception {
    System.out.println("Executing Struts2ListAction.prepare()...");

    ServletContext application = request.getSession().getServletContext();

    WebApplicationContext webApplicationContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(application);

    /*UserService */userService = (UserService) webApplicationContext.getBean("userService");

    if (userId == 0) {
      user = new User();
    }
    else {
      user = userService.findById(userId);
    }
  }

  public String execute() {
    System.out.println("Executing Struts2ListAction.execute()...");

    request.setAttribute("user", user);

    return SUCCESS;
  }

  public String view() {
    System.out.println("Executing Struts2ListAction.view()...");

    String id = request.getParameter("id");

    user = userService.findById(Long.parseLong(id));

    request.setAttribute("user", user);    

    return SUCCESS;
  }

  public String create() {
    System.out.println("Executing Struts2ListAction.create()...");

    user = userService.create();

    return SUCCESS;
  }

  public String save() {
    System.out.println("Executing Struts2ListAction.save()...");

    userService.update(user);

    return SUCCESS;
  }

  public String update() {
    System.out.println("Executing Struts2ListAction.update()...");

    String id = request.getParameter("id");

    User user = userService.findById(Long.parseLong(id));

    request.setAttribute("user", user);

    return SUCCESS;
  }

  public String delete() {
    System.out.println("Executing Struts2ListAction.delete()...");

    userService.delete(userId);

    return SUCCESS;
  }

 // public String doInput() {
 //   return INPUT;
//  }

  public Object getModel() {
    return user;
  }

}

