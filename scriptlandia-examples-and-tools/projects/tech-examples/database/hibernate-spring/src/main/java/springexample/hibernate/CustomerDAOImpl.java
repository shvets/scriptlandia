/*
 *  This file was created by Rick Hightower of ArcMinds Inc. 
 *
 */
package springexample.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;




public class CustomerDAOImpl extends HibernateDaoSupport implements CustomerDAO{
	
	


	public void addCustomer(Customer customer) {
		getHibernateTemplate().save(customer);
		// TODO Auto-generated method stub
		
	}

	public Customer getCustomerAccountInfo(Customer customer) {
		Customer cust = null;
		List list = getHibernateTemplate().find("from Customer customer " +
				                    "where customer.userId = ?" ,
				                    customer.getUserId()/*,Hibernate.STRING*/); 
		
		if(list.size() > 0){
			 cust = (Customer)  list.get(0);
		}
		
		return cust;
		
		
	}

}
