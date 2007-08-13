package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ItemManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class TestTilesController implements Controller {
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

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("greeting", "Hello, World!");

    return new ModelAndView("testTiles", "model", model);
  }

}
