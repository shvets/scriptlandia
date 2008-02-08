
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.hibernate.*;

import springexample.hibernate.*;

public class MyUnitTest extends TestCase {
  private static ApplicationContext ac = new FileSystemXmlApplicationContext(
                    new String[] {
                      "src/main/resources/beans.xml",
                      "src/test/resources/beans-junit.xml",
                     });

  @Override
  protected void setUp() throws Exception {
    super.setUp();

  }

  @Override
  protected void tearDown() throws Exception {

    super.tearDown();
  }

  public void testCreate1() throws Exception {
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

    CustomerDAO customerDAO = (CustomerDAO) ac.getBean("customerDAO");

    customerDAO.addCustomer(customer);
  }

  public void testList1() throws Exception {
    CustomerDAO customerDAO = (CustomerDAO) ac.getBean("customerDAO");

    Customer customer = new Customer();
    customer.setUserId("id1");

    Customer customerRecord = customerDAO.getCustomerAccountInfo(customer);

    System.out.println("Customer found: " + customerRecord);
  }

  /**
     * Runs the test case.
     */
  public static void main(String[] args) {
    TestSuite testSuite = new TestSuite();

    for(int i = 0; i < args.length; i++) {
      MyUnitTest test = (MyUnitTest)ac.getBean("myUnitTest");

      test.setName(args[i]);

      testSuite.addTest(test);
    }

    junit.textui.TestRunner.run(testSuite);
  }

}
