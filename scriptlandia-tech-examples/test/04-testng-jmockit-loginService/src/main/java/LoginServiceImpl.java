import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginServiceImpl implements LoginService {
  private UserDAO userDao = new UserDAOImpl();

  public boolean login(String userName, String password) {
    boolean valid = false;
    try {
         String passwordHash = null;
         MessageDigest md5 = MessageDigest.getInstance("MD5");
         md5.update(password.getBytes());
         //passwordHash = new String(md5.digest());

         User results = 
              userDao.loadByUsernameAndPassword(userName, password);
         if(results != null) {
              valid = true;
         }
    } catch (NoSuchAlgorithmException ignore) {}
    	
    return valid;
  }

}
   
