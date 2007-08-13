import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;

import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.xmlbeans.XmlBeansServiceFactory;



public class WSClientTest extends TestCase {

  public void testClient1() throws Exception {
    System.out.println("Executing testClient1()." );

    try {
      String serviceUrl = "http://localhost:9090/xfire-spring-test/services/EchoService";

//      Service serviceModel = new ObjectServiceFactory().create(Echo.class);        
      XmlBeansServiceFactory xsf = new XmlBeansServiceFactory();
      Service serviceModel = xsf.create(Echo.class);

      //XFire xfire = XFireFactory.newInstance().getXFire();
      //XFireProxyFactory factory = new XFireProxyFactory(xfire);      
      //Echo client = (Echo) factory.create(serviceModel, serviceUrl);

      Echo client = (Echo) new XFireProxyFactory().create(serviceModel, serviceUrl);


      String input = "test echo";

      String result = client.echo(input);

      System.out.println("Original: " + input);
      System.out.println("Echoed: " + result);
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

    testSuite.addTest(test);

    junit.textui.TestRunner.run(testSuite);
  }

}