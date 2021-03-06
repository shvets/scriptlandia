/*
 *  This file was created by Rick Hightower of ArcMinds Inc. 
 *
 */
package springexample.hibernate;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class CustomerDAOImpl extends JpaDaoSupport implements CustomerDAO {

  public void addCustomer(Customer customer) {
    getJpaTemplate().merge(customer);
  }

  public Customer getCustomerAccountInfo(Customer customer) {
    Customer cust = null;

    List list = getJpaTemplate().
        find("from Customer customer " + "where customer.userId = ?",
            customer.getUserId());

    if (list.size() > 0) {
      cust = (Customer) list.get(0);
    }

    return cust;
  }

}
