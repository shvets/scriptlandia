/*
 *  This file was created by Rick Hightower of ArcMinds Inc. 
 *
 */
package springexample.hibernate;

import java.util.List;

/**
 * @author Richard Hightower
 * ArcMind Inc. http://www.arc-mind.com
 */
public interface CustomerDAO {
	
	public abstract void addCustomer(Customer customer);
	
	public abstract Customer getCustomerAccountInfo(Customer customer);
}