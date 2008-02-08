package springexample.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CustomerDAOImpl extends HibernateDaoSupport implements CustomerDAO {

  public void addCustomer(Customer customer) {
    getHibernateTemplate().save(customer);
  }

  public Customer getCustomerAccountInfo(Customer customer) {
    Customer cust = null;
    List list = getHibernateTemplate().find("from Customer customer " +
        "where customer.userId = ?", customer.getUserId());

    if (list.size() > 0) {
      cust = (Customer) list.get(0);
    }

    return cust;
  }

}
