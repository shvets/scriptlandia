public class SimpleServiceImpl implements SimpleService {

  public Item service(Item item) {
    System.out.println("Executing " + getClass().getName() + ".service() method.");

    Item newItem = new Item();

    newItem.setName("Modified " + item.getName());
    newItem.setDescription("Modified " + item.getDescription());

    return newItem;
  }

}