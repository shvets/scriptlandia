import java.util.List;

public class DbTest {

  public static void main(String[] args) throws Throwable {
    BaseDAO dao = new BaseDAO();

    List list = dao.getList("selectAllUsers", null);

    System.out.println("list: " + list);
  }

}
