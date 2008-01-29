import com.opensymphony.xwork2.Action;
import struts2.action.Struts2Action;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class Struts2ActionTest extends BaseStrutsTestCase {

  public Struts2ActionTest(String name) {
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
		  Struts2Action action = createAction(Struts2Action.class, "/struts2", "delete");

		  action.setId(123);

		  String result = proxy.execute();

		  assertEquals(result, Action.SUCCESS);
	  } catch (Exception e) {
	    fail(e.getMessage());
	  }
  }

  /**
   * Invoke all interceptors and specify value of action class'
   * domain objects through request parameters.
   *
   * @throws Exception Exception
   */
  public void testInterceptorsBySettingRequestParameters() throws Exception {
	  try {
		  createAction(Struts2Action.class, "/struts2", "delete");

		  request.addParameter("id", "123");

		  String result = proxy.execute();

		  assertEquals(result, Action.SUCCESS);
	  } catch (Exception e) {
	    fail(e.getMessage());
	  }
  }

  /**
   * Skip interceptors and specify value of action class'
   * domain objects by setting them directly.
   *
   * @throws Exception Exception
   */
  public void testActionAndSkipInterceptors() throws Exception {
	  try {
		  Struts2Action action = createAction(Struts2Action.class, "/struts2", "delete");
		  action.prepare();

		  action.setId(123);

		  String result = action.delete();

		  assertEquals(result, Action.SUCCESS);
	  } catch (Exception e) {
	    fail(e.getMessage());
	  }
  }



  public void testValidation() throws Exception {
   Struts2Action action = createAction(Struts2Action.class, "/struts2", "delete");
   // lets forget to set a required field: action.setId(123);

   String result = proxy.execute();

   assertEquals(result, "input");
   assertTrue("Must have one field error", action.getFieldErrors().size() == 1);
  }

	public static void main(String[] args) {
	  TestSuite suite = new TestSuite();

	  suite.addTestSuite(Struts2ActionTest.class);

	  TestRunner.run(suite);
	}

}
