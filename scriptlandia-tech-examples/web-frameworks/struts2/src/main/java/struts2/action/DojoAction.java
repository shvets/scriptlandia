package struts2.action;

import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import org.apache.struts2.util.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

import business.UserService;
import business.User;

public class DojoAction extends ActionSupport implements ServletContextAware {
  private UserService userService;

  private ServletContext application;
  private int id;

  private InputStream inputStream;

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String userDescription() throws Exception {
    WebApplicationContext webApplicationContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(application);

    userService = (UserService) webApplicationContext.getBean("userService");

    User user = userService.findById(new Long(id).longValue());

    inputStream = new StringBufferInputStream(user.toJSONString());
    
    return "success";
  }

  public void setServletContext(ServletContext application) {
    this.application = application;
  }
}
