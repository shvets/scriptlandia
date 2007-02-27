package business;

import java.util.List;

public interface UserService {
  List<User> list();

  User create();

  void update(User user);

  void delete(long id);

  User findById(long id);
  
}
