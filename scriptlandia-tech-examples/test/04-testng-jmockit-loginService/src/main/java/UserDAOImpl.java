/**
* Provides database access for login related functions
*/
public class UserDAOImpl implements UserDAO {

     /**
     * Loads a User object for the record that
     * is returned with the same userName and password.
     * 
     * @parameter userName
     * @parameter password
     * @return    User
     */
  public User loadByUsernameAndPassword(String userName, String password) {
    User user = new User();

    user.userName = userName;
    user.password = password;

    return user;
  }

}
