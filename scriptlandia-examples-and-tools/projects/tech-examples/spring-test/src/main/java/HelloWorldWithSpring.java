import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;


public class HelloWorldWithSpring {

  public static void main(String[] args) throws Exception {

    BeanFactory factory = new XmlBeanFactory(new FileSystemResource("src/main/resources/beans.xml"));

    MessagePresentation presentation = (MessagePresentation) factory.getBean("presentation");

    presentation.view();
  }

}