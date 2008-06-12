package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ItemManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class Test2Controller extends AbstractController {
  /** Logger for this class and subclasses */
  protected final Log logger = LogFactory.getLog(getClass());

  private ItemManager itemManager;

  public ItemManager getItemManager() {
    return itemManager;
  }

  public void setItemManager(ItemManager itemManager) {
    this.itemManager = itemManager;
  }

  public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    logger.warn(getClass().getName() + ".handleRequest()3");

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("date", new Date().toString());
    model.put("items", getItemManager().getItems());

    return new ModelAndView("test2", "model", model);
  }

}
