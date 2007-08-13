package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import data.Item;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.object.MappingSqlQuery;

public class ItemManagerDaoImpl implements ItemManagerDao {

  /**
   * Logger for this class and subclasses
   */
  protected final Log logger = LogFactory.getLog(getClass());

  private DataSource dataSource;

  public List getItems() {
    MappingSqlQuery query =
            new MappingSqlQuery(dataSource, "SELECT id, name, price, expiration_date from item") {

      protected Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Item item = new Item();

        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setPrice(new Double(resultSet.getDouble("price")));
        item.setExpirationDate(resultSet.getDate("expiration_date"));

        return item;
      }
    };

    return query.execute();
  }

/*  public void updateItem() {
    SqlUpdate sqlUpdate =
        new SqlUpdate(dataSource, "update item set price = price * (100 + ?) / 100 where id = ?");

    sqlUpdate.declareParameter(new SqlParameter("increase", Types.INTEGER));
    sqlUpdate.declareParameter(new SqlParameter("ID", Types.INTEGER));
    sqlUpdate.compile();

    Object[] oa = new Object[2];
    oa[0] = new Integer(pct);
    oa[1] = new Integer(prod.getId());
    int count = su.update(oa);
  }
*/

  public void setDataSource(DataSource ds) {
    this.dataSource = ds;
  }

}
