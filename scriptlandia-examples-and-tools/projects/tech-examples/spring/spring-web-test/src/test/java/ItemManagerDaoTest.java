import java.util.List;

import dao.ItemManagerDaoImpl;
import data.Item;
import junit.framework.TestCase;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ItemManagerDaoTest extends TestCase {

  private ItemManagerDaoImpl imDao;

  public void setUp() throws Exception {
    super.setUp();

    imDao = new ItemManagerDaoImpl();

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.hsqldb.jdbcDriver");
    ds.setUrl("jdbc:hsqldb:db/test");
    ds.setUsername("sa");
    ds.setPassword("");

    imDao.setDataSource(ds);
  }

  public void tearDown() throws Exception {
    super.tearDown();

    imDao = null;
  }

  public void testGetItems() {
    List list = imDao.getItems();

    Item item1 = (Item) list.get(0);
    assertEquals("name1", item1.getName());

    Item item2 = (Item) list.get(1);
    assertEquals("name2", item2.getName());
  }

  /*public void testIncreasePrice() {
      List l1 = pmdao.getProductList();
      Product p1 = (Product) l1.get(0);
      assertEquals(new Double("5.78"), p1.getPrice());
      pmdao.increasePrice(p1, 10);
      List l2 = pmdao.getProductList();
      Product p2 = (Product) l2.get(0);
      assertEquals(new Double("6.36"), p2.getPrice());
  }
  */

}