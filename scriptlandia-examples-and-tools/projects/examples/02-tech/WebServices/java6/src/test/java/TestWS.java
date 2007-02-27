import javax.xml.ws.Endpoint;

public class TestWS {
  public static void main(String[] args) {
    Endpoint.publish(
       "http://localhost:8080/WebServiceExample/circlefunctions",
       new hello.CircleFunctions());
  }

}