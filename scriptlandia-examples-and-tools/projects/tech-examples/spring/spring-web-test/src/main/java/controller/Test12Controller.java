package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class Test12Controller implements Controller {

  protected final Log logger = LogFactory.getLog(getClass());

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    logger.info(getClass().getName() + ".handleRequest()");

    Map<String, Object> model = new HashMap<String, Object>();

    String rt = request.getParameter("rt");
    if(rt == null) {
      throw new Exception("testing exception handling...");
    }
    else {
      if(true) {
        throw new RuntimeException("testing runtime exception handling...");
      }
    }

    return new ModelAndView("test12", "model", model);
  }

}
