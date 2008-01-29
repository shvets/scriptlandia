package dao;

import business.User;
import business.UserService;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;

public class UserServiceImpl implements UserService {
  private static List<User> list = new ArrayList<User>();

  static {
    User user1 = new User();
    user1.setId(1);
    user1.setTitle("title1");
    user1.setFirstName("firstName1");
    user1.setLastName("lastName1");
    user1.setRole("role1");

    user1.setAdmin(false);

    list.add(user1);

    User user2 = new User();
    user2.setId(2);
    user2.setTitle("title2");
    user2.setFirstName("firstName2");
    user2.setLastName("lastName2");
    user2.setAdmin(false);
    user2.setRole("role2");

    list.add(user2);

    User user3 = new User();
    user3.setId(3);
    user3.setTitle("title3");
    user3.setFirstName("firstName3");
    user3.setLastName("lastName3");
    user3.setAdmin(true);
    user3.setRole("role3");

    list.add(user3);
  }

  public List<User> list() {
    return list;
  }

  public User create() {
    System.out.println("create");

    return new User();
  }

  private long getMaxId() {
    long maxId = 0;

    for (User u : list) {
      long id = u.getId();

      if(id > maxId) {
        maxId = id;
      }
    }

    return maxId;
  }

  public void update(User user) {
    System.out.println("update");

    if(user.getId() == 0) {
      user.setId(getMaxId()+1);
      list.add(user);
    }
    else {
      for (User u : list) {
        if(u.getId() == user.getId()) {
          try {
            BeanUtils.copyProperties(u, user);
          }
          catch(Exception e) {
            ;
          }

          break;
        }
      }
    }
  }

  public void delete(long id) {
    System.out.println("delete");

    for (User u : list) {
      if(u.getId() == id) {
        list.remove(u);
        break;
      }
    }
  }

  public User findById(long id) {
    System.out.println("findById");

    User user = null;

    for (User u : list) {
      if(u.getId() == id) {
        user = u;
        break;
      }
    }

    return user;
  }
}
