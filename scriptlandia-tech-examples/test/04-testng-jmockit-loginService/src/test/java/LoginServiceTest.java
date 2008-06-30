import mockit.Expectations;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import mockit.*;

/**
* Test case for LoginService.
*/
public class LoginServiceTest {

  private LoginServiceImpl service;
 
  @BeforeTest(groups ={"positive", "all", "negative"})  
  public void setupMocks() {
    service = new LoginServiceImpl();
  }


  /** 
   * This method will test the "rosy" scenario of  
   * passing a valid username and password and  
   * retrieveing the user. Once the user is returned  
   * to the service, the service will return true to  
   * the caller. 
   */  
  @Test(groups = {"positive", "all"})  
  public void testRosyScenario() {  
       final String userName = "testUserName";  
    
       Mockit.redefineMethods( UserDAOImpl.class, new Object() {  
            public User loadByUsernameAndPassword( String un, String password ) {  
                 assert un.equals( userName ) : "Username did not match";  
                 assert "testPassword".equals( password ) :   
                        "Password hash did not match";  
                 return new User();  
            }  
       } );  
    
       assert service.login( userName, "testPassword" ) :   
              "Expected true, but was false";  
  }  
    
  /** 
   * This method will test the negative of the "rosy"  
   * scenario of passing a valid username and password and  
   * retrieving the user. Once the user is returned  
   * to the service, the service will return true to  
   * the caller. 
   */  
  @Test(groups = {"negative", "all"})  
  public void testNotFoundScenario() {  
       final String userName = "notFoundUser";  
    
       Mockit.redefineMethods( UserDAOImpl.class, new Object() {  
            public User loadByUsernameAndPassword( String un, String password ) {  
                 assert un.equals( userName ) : "Username did not match";  
                 assert "testPassword".equals( password ) :   
                        "Password hash did not match";  
                 return null;  
            }  
       } );  
    
       assert !service.login( userName, "testPassword" ) :   
              "Expected false, but was true";  
  }  

}
