package struts2.action;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import business.User;
import business.UserService;

import java.util.List;

public class Struts2ListAction extends ActionSupport
       implements ServletRequestAware {
  private HttpServletRequest request;

  public void setServletRequest(HttpServletRequest httpServletRequest) {
    this.request = httpServletRequest;
  }

  public String execute() {
    System.out.println("Executing Struts2ListAction.execute()...");

    ServletContext application = request.getSession().getServletContext();

    WebApplicationContext webApplicationContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(application);

    UserService userService = (UserService) webApplicationContext.getBean("userService");

    List<User> daoList = userService.list();

    request.setAttribute("list", daoList);

    return SUCCESS;
  }

}
