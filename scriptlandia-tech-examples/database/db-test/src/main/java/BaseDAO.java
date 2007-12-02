import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseDAO {
  private final static Log log = LogFactory.getLog(BaseDAO.class);
  private static SqlMapClient sqlMap = null;

  static {
    try {
      String resource = "sqlmap-config.xml";
      Reader reader = Resources.getResourceAsReader(resource);

      sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);

      reader.close();
    } catch (Exception ex) {
      log.error("BaseDAO static block: " + ex);
      throw new RuntimeException("Error Initializing BaseDAO :" + ex);
    }
  }

  public synchronized List getList(String statementName, Object parameterObject)
          throws DaoException {
    List list = null;

    try {
      sqlMap.startTransaction();
      list = sqlMap.queryForList(statementName, parameterObject);

/*
Integer personPk = new Integer(5);
User person = (User) sqlMap.queryForObject (“getPerson”, personPk);
*/

      sqlMap.commitTransaction();
    } catch (SQLException e) {
      //try {
      //sqlMap.rollbackTransaction();
      //} catch (SQLException ex) {
      //throw new DaoException(e.fillInStackTrace());
      //}

      throw new DaoException(e.fillInStackTrace());
    }
    finally {
      try {
        sqlMap.endTransaction();
      } catch (SQLException e) {
        log.error(e);
        throw new DaoException(e);
      }
    }

    return list;
  }

  public synchronized Object getObject(String statementName, Object parameterObject)
          throws DaoException {
    Object result = null;

    try {
      sqlMap.startTransaction();
      result = sqlMap.queryForObject(statementName, parameterObject);
      sqlMap.commitTransaction();
    } catch (SQLException e) {
      /*try {
          sqlMap.rollbackTransaction();
      } catch (SQLException ex) {
          throw new DaoException(ex.fillInStackTrace());
      }
      */

      throw new DaoException(e.fillInStackTrace());
    }
    finally {
      try {
        sqlMap.endTransaction();
      } catch (SQLException e) {
        log.error(e);
        throw new DaoException(e);
      }
    }

    return result;
  }

  public synchronized int update(String statementName, Object parameterObject)
          throws DaoException {
    int result = 0;

    try {
        sqlMap.startTransaction();
        result = sqlMap.update(statementName, parameterObject);
        sqlMap.commitTransaction();
    } catch (SQLException e) {
      /*try {
          sqlMap.rollbackTransaction();
      } catch (SQLException ex) {
          throw new DaoException(ex.fillInStackTrace());
      }
      */

      throw new DaoException(e.fillInStackTrace());
    }
    finally {
      try {
        sqlMap.endTransaction();
      } catch (SQLException e) {
        log.error(e);
        throw new DaoException(e);
      }
    }

    return result;
  }

}
