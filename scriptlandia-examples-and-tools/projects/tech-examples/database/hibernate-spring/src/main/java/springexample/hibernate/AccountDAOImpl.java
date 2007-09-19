/*
 *  This file was created by Rick Hightower of ArcMinds Inc. 
 *
 */
package springexample.hibernate;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class AccountDAOImpl extends HibernateDaoSupport implements AccountDAO{
	
	


	public void addAccount(Account account) {
		getHibernateTemplate().save(account);
		// TODO Auto-generated method stub
		
	}

}
