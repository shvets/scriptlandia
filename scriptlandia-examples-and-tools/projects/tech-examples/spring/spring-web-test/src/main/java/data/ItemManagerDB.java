package data;

import java.util.List;

import dao.ItemManagerDao;

public class ItemManagerDB implements ItemManager {

  private ItemManagerDao imd;

  private List items;

  public void setItemManagerDao(ItemManagerDao imd) {
    this.imd = imd;
  }

  public List getItems() {
    return imd.getItems();
  }

}
