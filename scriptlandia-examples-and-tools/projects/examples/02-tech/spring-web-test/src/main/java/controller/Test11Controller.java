package controller;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import data.ItemManager;

public class Test11Controller implements Controller {

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
    
    return new ModelAndView("test11", "model", model);
  }

}
