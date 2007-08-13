package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.AbstractController;

public class Test4Controller extends AbstractController {

  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    logger.info(getClass().getName() + ".handleRequest()");
    
    setCacheSeconds(10);

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("Greeting", "Hello World");
    model.put("Server time", new Date());

    View view = (View) getApplicationContext().getBean("plainTextView");

    return new ModelAndView(view, model);
  }

}
