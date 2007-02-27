package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class Test6Controller extends MultiActionController {

  public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
    logger.info(getClass().getName() + ".init()");

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("mode", "none");

    return new ModelAndView("test6", "model", model);
  }

  public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
    logger.info(getClass().getName() + ".view()");

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("mode", "view");

    return new ModelAndView("test6", "model", model);
  }

  public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) {
    logger.info(getClass().getName() + ".edit()");

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("mode", "edit");

    return new ModelAndView("test6", "model", model);
  }

}
