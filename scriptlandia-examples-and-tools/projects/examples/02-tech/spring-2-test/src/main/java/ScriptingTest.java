import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ScriptingTest {

  public static void main(String[] args) throws Exception {
    ApplicationContext factory = new ClassPathXmlApplicationContext("beans.xml");

    Coconut coconut = (Coconut) factory.getBean("coconut1");
    coconut.drinkThemBothUp();

    Coconut coconut2 = (Coconut) factory.getBean("coconut2");
    coconut2.drinkThemBothUp();

    Coconut coconut3 = (Coconut) factory.getBean("coconut3");
    coconut3.drinkThemBothUp();
  }

}