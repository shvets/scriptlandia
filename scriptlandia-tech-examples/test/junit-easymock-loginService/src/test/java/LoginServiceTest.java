import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.eq;

/**
* Test case for LoginService.
*/
public class LoginServiceTest {

     private LoginServiceImpl service;

    @Before
    public void setup() {
          service = new LoginServiceImpl();
     }

    /**
     * This method will test the "rosy" scenario of passing a valid
     * username and password and retrieveing the user.  Once the user
     * is returned to the service, the service will return true to
     * the caller.
     */
     @Test
     public void rosyScenario() {
          String userName = "testUserName";
          String password = "testPassword";
//          String passwordHash = "_O¶&I7__3Ni=.";

          User results = new User();

          UserDAO mockDao = createStrictMock(UserDAO.class);

          service.setUserDAO(mockDao);

          expect(mockDao.loadByUsernameAndPassword(eq(userName), eq(password))).andReturn(results);

          replay(mockDao);

          assertTrue(service.login(userName, password));
          verify(mockDao);

     }

}
