package springexample.hibernate;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

  public static ClassPathXmlApplicationContext appContext = null;

  public static void main(String[] args) {
    ClassPathXmlApplicationContext appContext =
        new ClassPathXmlApplicationContext(new String[]{ "beans.xml" });

    Customer customer = new Customer();

    customer.setEmail("aaa@bbb.com");
    customer.setUserId("id1");
    customer.setPassword("p1");
    customer.setFirstName("A1");
    customer.setLastName("B1");

    Account account = new Account();
    account.setAccountName("Checking Account");
    account.setType("C");
    account.setCreateDate(new Date());
    account.setUpdateDate(new Date());
    account.setBalance(500.00);
    customer.addAccount(account);

    CustomerDAO customerDAO = (CustomerDAO) appContext.getBean("customerDAO");

    customerDAO.addCustomer(customer);

    Customer customerRecord = customerDAO.getCustomerAccountInfo(customer);

    System.out.println("Customer found: " + customerRecord.getLastName());
  }

}
