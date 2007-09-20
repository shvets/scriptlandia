package springexample.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AccountDAOImpl extends HibernateDaoSupport implements AccountDAO {

  public void addAccount(Account account) {
    getHibernateTemplate().save(account);
  }

}
