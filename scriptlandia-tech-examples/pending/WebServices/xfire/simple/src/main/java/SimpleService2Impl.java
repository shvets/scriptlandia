import javax.jws.WebService;

@WebService(
  serviceName = "SimpleService2",
  endpointInterface = "SimpleService2"
)
public class SimpleService2Impl implements SimpleService2 {

  public Item service(Item item) {
    System.out.println("Executing " + getClass().getName() + ".service() method.");

    Item newItem = new Item();

    newItem.setName("Modified " + item.getName());
    newItem.setDescription("Modified " + item.getDescription());

    return newItem;
  }

}