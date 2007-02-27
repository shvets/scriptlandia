package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ItemManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Test8Controller implements Controller {

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

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("items", getItemManager().getItems());

    return new ModelAndView("test8", "model", model);
  }

}
