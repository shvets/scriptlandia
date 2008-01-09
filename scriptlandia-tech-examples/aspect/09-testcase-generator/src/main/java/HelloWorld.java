public class HelloWorld {
        
  public void test1()  {
    System.out.println("Executing test1().");

    test2();
  }

  public void test2()  {
    System.out.println("Executing test2().");
  }

  public static void main (String[] args) {
    System.out.println("Hello World!");

    HelloWorld helloWorld = new HelloWorld();

    helloWorld.test1();
    helloWorld.test2();
  }

}
