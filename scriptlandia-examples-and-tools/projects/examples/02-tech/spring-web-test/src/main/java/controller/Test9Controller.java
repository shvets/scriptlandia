package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ItemManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class Test9Controller implements Controller {

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

    return new ModelAndView("test9");
  }

}
