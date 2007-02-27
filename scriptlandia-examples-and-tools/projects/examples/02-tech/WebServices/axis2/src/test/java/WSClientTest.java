import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.async.AsyncResult;
import org.apache.axis2.client.async.Callback;


public class WSClientTest extends TestCase {
  public void testClient() throws Exception {
    try {

    System.getProperties().put("proxySet", "true");
    System.getProperties().put("proxyPort", "8080");
    System.getProperties().put("proxyHost", "proxyHost");

      String serviceUrl = "http://localhost:8080/axis2-test/services/BookService?wsdl";

      Options options = new Options();
      options.setTo(new EndpointReference(serviceUrl));
      ServiceClient servicClient = new ServiceClient();

      servicClient.setOptions(options);


      OMElement payload = getPayload();

      Callback callback = new Callback() {
          public void onComplete(AsyncResult result) {
              System.out.println(result.getResponseEnvelope());
          }

          public void onError(Exception e) {
              e.printStackTrace();
          }
      };

      //System.out.println("Result: " + servicClient.sendRobust(getPayload()));

      //OMElement result = servicClient.sendReceive(getPayload());
      servicClient.sendReceiveNonBlocking(payload, callback);

      while (!callback.isComplete()) {
        Thread.sleep(1000);
      }

      //System.out.println("Result: " + result);
    }
    catch(Throwable t) {
      t.printStackTrace();
      fail(t.getMessage());
    }
  }	

  private static OMElement getPayload() {
      OMFactory fac = OMAbstractFactory.getOMFactory();
      OMNamespace omNs = fac.createOMNamespace(
              "http://ws.apache.org/axis2/samples/book/xsd/", "example1");

      OMElement method = fac.createOMElement("getBooks", omNs);
//      OMElement value = fac.createOMElement("Text", omNs);
//      value.addChild(fac.createOMText(value, "Axis2 Echo String "));
//      method.addChild(value);

      return method;
  }

  /**
   * Runs the test case.
   */
  public static void main(String[] args) {
    TestSuite testSuite = new TestSuite();

    WSClientTest test = new WSClientTest();

    test.setName("testClient");

    testSuite.addTest(test);

    junit.textui.TestRunner.run(testSuite);
  }

}
