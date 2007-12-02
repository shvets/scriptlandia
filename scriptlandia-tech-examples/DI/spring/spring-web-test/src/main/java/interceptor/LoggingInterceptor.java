package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingInterceptor extends HandlerInterceptorAdapter {
  /** Logger for this class and subclasses */
  protected final Log logger = LogFactory.getLog(getClass());

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
          throws Exception {
    logger.debug("Calling " + handler + ".preHandle()...");    

    return super.preHandle(request, response, handler);
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response,
                         Object handler, ModelAndView modelAndView) throws Exception {
    logger.debug("Calling " + handler + ".postHandle()...");

    super.postHandle(request, response, handler, modelAndView);
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                              Object handler, Exception exception) throws Exception {
    logger.debug("Calling " + handler + ".afterCompletion()...");

    super.afterCompletion(request, response, handler, exception);
  }

}