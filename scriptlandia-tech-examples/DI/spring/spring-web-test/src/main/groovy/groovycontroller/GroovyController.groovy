// GroovyController.groovy

package groovycontroller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.Controller

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class GroovyController implements Controller {
  /** Logger for this class and subclasses */
  protected final Log logger = LogFactory.getLog(getClass());

  String message

  ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse httpServletResponse) {
    logger.info(getClass().getName() + ".handleRequest()");

    Map model = new HashMap();
    model.put("message", message);
    model.put("firstName", "Bartolomew");
    model.put("lastName", "Simpson");

    return new ModelAndView("test-groovy", "model", model);
  }

}