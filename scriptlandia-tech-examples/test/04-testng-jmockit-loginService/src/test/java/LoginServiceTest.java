import mockit.Expectations;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import mockit.Expectations;

/**
* Test case for LoginService.
*/
public class LoginServiceTest extends Expectations {

  private LoginServiceImpl service;
  UserDAO mock;

  @BeforeTest
  public void setupMocks() {
    service = new LoginServiceImpl();
    service.setUserDao( mock );
  }


  /**
   * This method will test the "rosy" scenario of passing a valid 
   * username and password and retrieveing the user. Once the user 
   * is returned to the service, the service will return true to 
   * the caller.
   */
  @Test
  public void testRosyScenario() {
  	final User results = new User();
  	final String userName = "testUserName";
  	final String password = "testPassword";
  	//String passwordHash = "??&I7???Ni=.";

      invokeReturning(mock.loadByUsernameAndPassword( userName, password ), results );
         
      endRecording();

      assert service.login( userName, password ) : "Expected true, but was false";
  }


}
