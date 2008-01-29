import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;
import junit.framework.TestCase;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.JspSupportServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;

import java.util.HashMap;

/**
 * @author Zarar Siddiqi
 * http://arsenalist.com/2007/06/18/unit-testing-struts-2-actions-spring-junit/
 */
public abstract class BaseStrutsTestCase extends TestCase {
  private static final String CONFIG_LOCATIONS = "WEB-INF/applicationContext.xml";

  private static ApplicationContext applicationContext;
  protected static HashMap session = new HashMap();

	private Dispatcher dispatcher;
  protected ActionProxy proxy;

  protected static MockServletContext servletContext;
  protected static MockServletConfig servletConfig;
  protected MockHttpServletRequest request;
  protected MockHttpServletResponse response;

  public BaseStrutsTestCase(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    if (applicationContext == null) {
      // this is the first time so initialize Spring context
      servletContext = new MockServletContext();
      servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
          CONFIG_LOCATIONS);
      applicationContext = (new ContextLoader()).initWebApplicationContext(servletContext);

      // Struts JSP support servlet (for Freemarker)
      new JspSupportServlet().init(new MockServletConfig(servletContext));
    }

    // Dispatcher is the guy that actually handles all requests.  Pass in
    // an empty. Map as the parameters but if you want to change stuff like
    // what config files to read, you need to specify them here.  Here's how to
    // scan packages for actions (thanks to Hardy Ferentschik - Comment 66)
    // (see Dispatcher's source code)
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("actionPackages", "struts2.action");
    dispatcher = new Dispatcher(servletContext, params);
    dispatcher.init();

    Dispatcher.setInstance(dispatcher);
  }

  /**
   * Created action class based on namespace and name
   *
   * @param clazz     Class for which to create Action
   * @param namespace Namespace of action
   * @param name      Action name
   * @return Action class
   * @throws Exception Catch-all exception
   */
  @SuppressWarnings("unchecked")
  protected <T> T createAction(Class<T> clazz, String namespace, String name)
      throws Exception {

    // create a proxy class which is just a wrapper around the action call.
    // The proxy is created by checking the namespace and name against the
    // struts.xml configuration
    proxy = dispatcher.getContainer().getInstance(ActionProxyFactory.class).
        createActionProxy(
            namespace, name, null, true, false);

    // by default, don't pass in any request parameters
    proxy.getInvocation().getInvocationContext().setParameters(new HashMap());
	proxy.getInvocation().getInvocationContext().setSession(session);

    // do not execute the result after executing the action
    proxy.setExecuteResult(true);

    // set the actions context to the one which the proxy is using
    ServletActionContext.setContext(proxy.getInvocation().getInvocationContext());

    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();

    ServletActionContext.setRequest(request);
    ServletActionContext.setResponse(response);
    ServletActionContext.setServletContext(servletContext);

    return (T) proxy.getAction();
  }

}
