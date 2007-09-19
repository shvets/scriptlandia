package springexample.hibernate;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;


public class CreateBankCustomerClient {
	
	public static ClassPathXmlApplicationContext appContext = null;
	
	public static void main(String[] args){
	try
	 {
	System.out.println("CreateBankCustomerClient started");
   
    
    ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {
        "springexample-hibernate.xml"
    });
    
	System.out.println("Classpath loaded");
	
	
	
	 
	Customer customer = new Customer();
	
	customer.setEmail("raj@malhotra.com");
	customer.setUserId("rajmalhotra");
	customer.setPassword("xxxxx");
	customer.setFirstName("Raj");
	customer.setLastName("Malhotra");

	
	
	Account acc = new Account();
	acc.setAccountName("Checking Account-Raj Malhotra");
	acc.setType("C");
	acc.setCreateDate(new Date());
	acc.setUpdateDate(new Date());
	acc.setBalance(new Double(500.00));
	customer.addAccount(acc);
	

	CustomerDAOImpl customerDAOImpl = (CustomerDAOImpl)
	appContext.getBean("customerDAOTarget");
	
	customerDAOImpl.addCustomer(customer);
	
	Customer customerRecord = customerDAOImpl.getCustomerAccountInfo(customer);
	
	System.out.println("Customer Found , User Id is " + customerRecord.getUserId());
	
	
	System.out.println("CreateBankCustomerClient end");
	 }
	catch(Exception e){
		e.printStackTrace();
	}
	}

	
	 private static void createDatabaseSchema() throws Exception {
	        LocalSessionFactoryBean sessionFactory = (LocalSessionFactoryBean) 
	        	appContext.getBean("&frameworkSessionFactory");

	        sessionFactory.dropDatabaseSchema();
	        sessionFactory.createDatabaseSchema();
	    }
}
