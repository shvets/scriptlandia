import mockit.Expectations;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import mockit.Expectations;

/**
* Test case for LoginService.
*/
public class LoginServiceTest extends Expectations {

  private LoginServiceImpl service;
  UserDAO mockDao;

  @BeforeTest
  public void setupMocks() {
    service = new LoginServiceImpl();
    service.setUserDao( mockDao );
  }


  /**
   * This method will test the "rosy" scenario of passing a valid 
   * username and password and retrieveing the user. Once the user 
   * is returned to the service, the service will return true to 
   * the caller.
   */
  @Test
  public void testRosyScenario() {
  	User results = new User();
  	String userName = "testUserName";
  	String password = "testPassword";
  	//String passwordHash = "??&I7???Ni=.";

  	invokeReturning( 
               mockDao.loadByUsernameAndPassword( userName, 
                                                  password ), 
               results );
  	endRecording();

  	assert service.login( userName, password ) : "Expected true, but was false";
  }


}
