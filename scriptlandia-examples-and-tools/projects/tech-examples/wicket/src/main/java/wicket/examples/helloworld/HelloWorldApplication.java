package wicket.examples.helloworld;

import wicket.protocol.http.WebApplication;

public class HelloWorldApplication extends WebApplication {

  public HelloWorldApplication() {}
    
  public Class getHomePage() {
    return HelloWorld.class;
  }

}
