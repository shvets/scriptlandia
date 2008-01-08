package actions;

public class HelloWorld2Action  {
  private String greeting;

  public String getGreeting() {
    return greeting;
  }

  public String execute() {
    greeting = "The server time is " + new java.util.Date().toString();
    return "success";
  }

}