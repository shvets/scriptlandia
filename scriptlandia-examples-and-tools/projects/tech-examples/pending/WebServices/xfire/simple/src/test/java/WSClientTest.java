import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;


public class WSClientTest extends TestCase {

  public void testClient1() throws Exception {
    System.out.println("Executing testClient1()." );

    try {

      Service serviceModel = new ObjectServiceFactory().create(SimpleService.class);        

      XFire xfire = XFireFactory.newInstance().getXFire();

      XFireProxyFactory factory = new XFireProxyFactory(xfire);      
      //String serviceUrl = "xfire.local://Banking" ;         

      String serviceUrl = "http://localhost:9090/xfire-simple-test/services/SimpleService";

      SimpleService client = (SimpleService) factory.create(serviceModel, serviceUrl);

      Item item = new Item();
      item.setName("name1");
      item.setDescription("description 1");

      Item result = client.service(item);

      System.out.println("Original: " + item);
      System.out.println("Result: " + result);
    }
    catch(Throwable t) {
      t.printStackTrace();
      fail(t.getMessage());
    }
  }	

  public void testClient2() throws Exception {
    System.out.println("Executing testClient2()." );

    try {

      Service serviceModel = new ObjectServiceFactory().create(SimpleService2.class);        

      //Service serviceModel = new AnnotationServiceFactory(new Jsr181WebAnnotations(),
      //    XFireFactory.newInstance().getXFire().getTransportManager()).create(SimpleService2.class);

      //create a proxy for the deployed service
      XFire xfire = XFireFactory.newInstance().getXFire();
      XFireProxyFactory factory = new XFireProxyFactory(xfire);      
      //String serviceUrl = "xfire.local://Banking" ;         

      String serviceUrl = "http://localhost:9090/xfire-test/services/SimpleService2";

      SimpleService2 client = (SimpleService2) factory.create(serviceModel, serviceUrl);

      Item item = new Item();
      item.setName("name1");
      item.setDescription("description 1");

      Item result = client.service(item);

      System.out.println("Original: " + item);
      System.out.println("Result: " + result);
    }
    catch(Throwable t) {
      t.printStackTrace();
      fail(t.getMessage());
    }
  }	

  /**
   * Runs the test case.
   */
  public static void main(String[] args) {
    TestSuite testSuite = new TestSuite();

    WSClientTest test = new WSClientTest();

    test.setName("testClient1");
    test.setName("testClient2");

    testSuite.addTest(test);

    junit.textui.TestRunner.run(testSuite);
  }

}