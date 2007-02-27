package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ItemManager;
import data.Item;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class Test5Controller implements Controller {
  /** Logger for this class and subclasses */
  protected final Log logger = LogFactory.getLog(getClass());

  private ItemManager itemManager;

  public ItemManager getItemManager() {
    return itemManager;
  }

  public void setItemManager(ItemManager itemManager) {
    this.itemManager = itemManager;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    logger.info(getClass().getName() + ".handleRequest()");

    List items = getItemManager().getItems();

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("date", new Date().toString());
    model.put("items", items);

    Item newItem = new Item();
    newItem.setId(items.size());

    model.put("newItem", newItem);

    return new ModelAndView("test5", "model", model);
  }

}
