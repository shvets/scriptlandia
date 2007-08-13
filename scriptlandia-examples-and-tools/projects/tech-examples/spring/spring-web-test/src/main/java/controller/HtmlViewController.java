package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class HtmlViewController extends org.springframework.web.servlet.mvc.UrlFilenameViewController {

  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
    logger.info(getClass().getName() + ".handleRequest()");

    ModelAndView modelAndView = super.handleRequestInternal(request, response);

    modelAndView.setViewName("htmlView");

    return modelAndView;
  }

}
