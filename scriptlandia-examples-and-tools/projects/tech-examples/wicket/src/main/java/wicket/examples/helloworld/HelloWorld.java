package wicket.examples.helloworld;

import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;

public class HelloWorld extends WebPage {

  public HelloWorld() {
    add(new Label("message", "Hello World!"));
  }

}