package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class Test13Controller extends AbstractController {

  public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    logger.info(getClass().getName() + ".handleRequest()");

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("greeting", "Hello, World!");

    return new ModelAndView("test13", "model", model);
  }

}
