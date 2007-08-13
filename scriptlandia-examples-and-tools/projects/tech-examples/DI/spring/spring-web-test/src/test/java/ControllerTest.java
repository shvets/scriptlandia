import java.util.List;
import java.util.Map;

import controller.Test1Controller;
import controller.UserController;
import data.Item;
import data.ItemManager;
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
              "src/main/webapp/WEB-INF/spring-dispatcher-servlet.xml",
              "src/main/webapp/WEB-INF/spring-data.xml"
            });
  }

  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testHandleRequest() throws Exception {
    Test1Controller controller = (Test1Controller) applicationContext.getBean("testController");
    ModelAndView modelAndView = controller.handleRequest(null, null);
    Map model = modelAndView.getModel();
    List items = (List) ((Map) model.get("testControllerModel")).get("items");

    Item item1 = (Item) items.get(0);
    assertEquals("name1", item1.getName());

    Item item2 = (Item) items.get(1);
    assertEquals("name2", item2.getName());
  }

  public void testEdit() throws Exception {
    UserController controller = (UserController) applicationContext.getBean("userController");

    ItemManager itemManager = (ItemManager) applicationContext.getBean("itemManager");

    Item item = new Item();
    item.setId(4);
    item.setName("name4");
    item.setPrice(12.4);

    itemManager.getItems().add(item);

    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/edit.spring");
    MockHttpServletResponse response = new MockHttpServletResponse();

    request.addParameter("id", item.getId().toString());

    ModelAndView modelAndView = controller.handleRequest(request, response);
    assertEquals("edit", modelAndView.getViewName());

    //Map model = modelAndView.getModel();
    //assertEquals(item, model.get(controller.getCommandName()));
  }

}
