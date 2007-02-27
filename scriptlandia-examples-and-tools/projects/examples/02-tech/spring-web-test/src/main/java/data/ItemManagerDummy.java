package data;

import java.util.List;

public class ItemManagerDummy implements ItemManager {

  private static final long serialVersionUID = -1519256843809596805L;

  private List items;

  public void setItems(List items) {
    this.items = items;
  }

  public List getItems() {
    return items;
  }

}
