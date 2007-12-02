import java.util.List;
import java.util.Map;

import controller.TestController;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ControllerTest extends TestCase {

  private ApplicationContext applicationContext;

  public void setUp() throws Exception {
    super.setUp();

    applicationContext = new FileSystemXmlApplicationContext(
            new String[] {
              "src/main/webapp/WEB-INF/spring-dispatcher-servlet.xml"
            });
  }

  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testHandleRequest() throws Exception {
    TestController controller = (TestController) applicationContext.getBean("testController");
    ModelAndView modelAndView = controller.handleRequest(null, null);
    Map model = modelAndView.getModel();
    String firstName = (String) ((Map) model.get("model")).get("first_name");
    String lastName = (String) ((Map) model.get("model")).get("last_name");

    assertEquals(firstName, "oleg");
    assertEquals(lastName, "konovalov");
  }

}
