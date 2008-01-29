import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.spring.StrutsSpringObjectFactory;
import org.springframework.mock.web.MockServletContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.ObjectFactory;

/**
* Class for easier support of Struts related
* testing. Takes care of all the configuration details
* that allow test classes to create beans (Spring),
* actions (Struts), intercepted actions (Struts).
* Class is singleton to minimize hit of initializing
* Struts and related infrastructure (e.g. Hibernate).
*
* Adapted from code from "The Arsenalist" (http://arsenalist.com/),
* see http://arsenalist.com/2007/06/18/unit-testing-struts-2-actions-spring-junit/
*/
public class StrutsTestCaseSupport {

   /**
    * Singleton variable
    */
   public static StrutsTestCaseSupport _theInstance = null;

   /**
    * Servlet context
    */
   private ServletContext servletContext = null;

   /**
    * Request dispatcher
    */
   private Dispatcher dispatcher = null;

   /**
    * Singleton access
    */
   public static synchronized StrutsTestCaseSupport getInstance()
       throws Exception {
       if ( _theInstance == null ) {
           _theInstance = new StrutsTestCaseSupport();
       }
       return _theInstance;
   }

   /**
    * Class constructor, take care of Struts initializations
    */
   private StrutsTestCaseSupport ()
       throws Exception {
       String[] config = new String[] { "/WEB-INF/applicationContext.xml" };

       // Link the servlet context and the Spring context
       servletContext = new MockServletContext(new FileSystemResourceLoader());
       XmlWebApplicationContext appContext = new XmlWebApplicationContext();
       appContext.setServletContext(servletContext);
       appContext.setConfigLocations(config);
       appContext.refresh();
       servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, appContext);

       // Use spring as the object factory for Struts
       StrutsSpringObjectFactory ssf = new StrutsSpringObjectFactory(null, null, servletContext);
       ssf.setApplicationContext(appContext);
       StrutsSpringObjectFactory.setObjectFactory(ssf);

       // Dispatcher is the guy that actually handles all requests.  Pass in
       // an empty Map as the parameters but if you want to change stuff like
       // what config files to read, you need to specify them here
       // (see Dispatcher's source code)
       dispatcher = new Dispatcher(servletContext, new HashMap());
       dispatcher.init();
       Dispatcher.setInstance(dispatcher);
   }

   /**
    * create a bean from the object factory (all wired up from Spring)
    *
    * @param beanName the name of the bean to get from the object factory
    * @param extraContext any extra content information to pass along to the bean building
    *        process
    * @return the object factory created bean
    * @throws Exception on processing, configuration errors, test failure
    */
   public Object createBean ( String beanName, Map extraContext )
   throws Exception {
       ObjectFactory objectFactory = dispatcher.getContainer().getInstance(ObjectFactory.class);
       return objectFactory.buildBean(beanName,extraContext);
   }

   /**
    * create an action proxied by it's interceptor stack
    *
    * @param actionName the name/id for the action
    * @param actionNameSpace the namespace for the action
    * @return the proxyed action
    * @throws Exception on processing, configuration errors, test failure
    */
   public ActionProxy createActionProxy ( String actionName, String actionNameSpace )
   throws Exception {
       // create a proxy class which is just a wrapper around the action call.
       // The proxy is created by checking the namespace and name against the
       // struts.xml configuration
       ActionProxy proxy = dispatcher.getContainer().getInstance(ActionProxyFactory.class).createActionProxy(actionNameSpace, actionName, null, true, false);

       // by default, don't pass in any request parameters
       proxy.getInvocation().getInvocationContext().setParameters(new HashMap());

       // by default, pass along an empty session map
       proxy.getInvocation().getInvocationContext().setSession(new HashMap());

       // set the actions context to the one which the proxy is using
       ServletActionContext.setContext(proxy.getInvocation().getInvocationContext());
       MockHttpServletRequest request = new MockHttpServletRequest();
       MockHttpServletResponse response = new MockHttpServletResponse();
       ServletActionContext.setRequest(request);
       ServletActionContext.setResponse(response);
       ServletActionContext.setServletContext(servletContext);

       // set proper URI
       request.setRequestURI(actionNameSpace + "/" + actionName);

       return proxy;
   }

   /**
    * create an action proxied by it's interceptor stack
    *
    * @param actionName the name/id for the action
    * @param actionNameSpace the namespace for the action
    * @param sessionMap the request/invocation session map (for http session map mocking)
    * @return the proxyed action
    * @throws Exception on processing, configuration errors, test failure
    */
   public ActionProxy createActionProxy ( String actionName, String actionNameSpace, Map sessionMap )
   throws Exception {

       // create an action proxy as usual
       ActionProxy actionProxy = createActionProxy(actionNameSpace,actionName);

       // set the session map in the action proxy's invocation
       actionProxy.getInvocation().getInvocationContext().setSession(sessionMap);

       return actionProxy;
   }

   /**
    * create an action object, bypass all it's stacks. Have it properly injected
    * according to configurations.
    *
    * @param actionName the name/id for the action
    * @param actionNameSpace the namespace for the action
    * @return the properly injected action
    * @throws Exception on processing, configuration errors, test failure
    */
   public Object createAction ( String actionName, String actionNameSpace )
   throws Exception {
       ActionProxy actionProxy = createActionProxy(actionName,actionNameSpace);
       return actionProxy.getAction();
   }

   /**
    * Access to the proxy's response content as a string
    * @param proxy the proxy to get the string response for
    * @return the proxy's response content as a string
    */
   public static String getResponseContentAsString ( ActionProxy proxy )
       throws java.io.UnsupportedEncodingException {
       return ((MockHttpServletResponse)ServletActionContext.getResponse()).getContentAsString();
   }

   /**
    * Set a hostname in proxy request
    * @param proxy the proxy to set hostname to
    * @param serverName the server name to set for this proxy's call
    */
   public static void setRequestServerName ( ActionProxy proxy, String serverName ) {
       ((MockHttpServletRequest)ServletActionContext.getRequest()).setServerName(serverName);
   }
}