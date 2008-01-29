import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import struts2.action.Struts2Action;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import junit.textui.TestRunner;

import java.util.Map;
import java.util.HashMap;

//http://fassisrosa.blogspot.com/2007/03/unit-testing-struts-20-part-2.html
// http://fassisrosa.blogspot.com/2007/09/unit-testing-struts-20-part-3.html
public class Struts2ActionTest2 extends TestCase {

  public Struts2ActionTest2(String name) {
    super(name);
  }

  /**
   * Invoke all interceptors and specify value of the action
   * class' domain objects directly.
   *
   * @throws Exception Exception
   */
  public void testInterceptorsBySettingDomainObjects()  throws Exception {
	  try {
		  //YourClass yourInstance = (YourClass)StrutsTestCaseSupport.getInstance().createBean("yourBeanId",new HashMap());

/*
// create action for ActionSearchTest
		  Map requestParameters = new HashMap();
		  requestParameters.put("searchMode","quick");
		  requestParameters.put("searchText","Testing");
		  Map actionContext = StrutsTestCaseSupport.getInstance().buildActionContext("struts.assisrosa.com","get","/search/results",requestParameters);

		  // create the proxy for the action, this encapsulates all
		  // the interception stack up to the real action
		  ActionProxy proxy = StrutsTestCaseSupport.getInstance().createActionProxy("results","/search",actionContext);

		  // let the full stack run
		  String result = proxy.execute();

		  // confirm result, any exception thrown will cause test to fail
		  assert result.equals("success");
*/		  
		    //  ActionProxy proxy = StrutsTestCaseSupport.getInstance().createActionProxy(yourActionId,yourContextPath);
		     // MyActionClass myActionInstance = (MyActionClass)StrutsTestCaseSupport.getInstance().createAction(yourActionId,yourContextPath);

		  /*
MyAction myAction = (MyAction) StrutsTestCaseSupport.getInstance().createAction("hello","/site",actionContext);
        String result = myAction.execute();
        assert result.equals("myExpectedResult");
        	  */
	  } catch (Exception e) {
	    fail(e.getMessage());
	  }
  }

 	public static void main(String[] args) {
	  TestSuite suite = new TestSuite();

	  suite.addTestSuite(Struts2ActionTest2.class);

	  TestRunner.run(suite);
	}

}