import javax.jws.WebService;

@WebService( targetNamespace = "http://org.sf.ws.simpleservice/SimpleService2" )
public interface SimpleService2 {

  Item service(Item item);

}
