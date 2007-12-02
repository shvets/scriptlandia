import junit.framework.TestCase;

import java.util.List;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;


public class WSClientTest extends TestCase {
  public void testClient() throws Exception {
    try {

    System.getProperties().put("proxySet", "true");
    System.getProperties().put("proxyPort", "8080");
    System.getProperties().put("proxyHost", "proxyHost");

    System.getProperties().put("http.proxySet", "true");
    System.getProperties().put("http.proxyPort", "8080");
    System.getProperties().put("http.proxyHost", "proxyHost");


      //create a metadata of the service		
      Service serviceModel = new ObjectServiceFactory().create(BabelFishService.class);        
      System.out.println("callSoapServiceLocal(): got service model." );

      //create a proxy for the deployed service
      XFire xfire = XFireFactory.newInstance().getXFire();
      XFireProxyFactory factory = new XFireProxyFactory(xfire);      
      //String serviceUrl = "xfire.local://Banking" ;         

      String serviceUrl = "http://www.xmethods.com/sd/2001/BabelFishService.wsdl";

      BabelFishService client = (BabelFishService) factory.create(serviceModel, serviceUrl);

      //Book[] books = client.BabelFish();
      String translated = client.BabelFish("en_es", "Hello World");

      System.out.println("translated: " + translated);
    }
    catch(Throwable t) {
      t.printStackTrace();
      fail(t.getMessage());
    }
  }	

}