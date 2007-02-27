package controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Item;
import data.ItemManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.RequestUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class UserController extends SimpleFormController {

  private ItemManager itemManager;

  public ItemManager getItemManager() {
    return itemManager;
  }

  public void setItemManager(ItemManager itemManager) {
    this.itemManager = itemManager;
  }

public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                             Object command, BindException errors) throws Exception {

    List items = itemManager.getItems();

    Item item = (Item) command;

    if(item.getId() == null) {
      items.add(item);
    }
    else {
      items.set(indexOf(item), item);
    }

    String op = request.getParameter("submit");

    if(op != null && op.equalsIgnoreCase("delete")) {
      items.remove(indexOf(item));
    }

    return new ModelAndView(new RedirectView(getSuccessView()));
  }

  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);

    binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, false));

    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    binder.registerCustomEditor(Double.class, null, new CustomNumberEditor(Double.class, numberFormat, true));
    binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Double.class, numberFormat, true));
    binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Double.class, numberFormat, true));
  }

  protected Object formBackingObject(HttpServletRequest request) {
    Item backingObject;

    int itemId = RequestUtils.getIntParameter(request, "itemId", 0);

    if(itemId == 0) {
      backingObject = new Item();
    }
    else {
      backingObject = (Item)getElement(itemId);
    }

    return backingObject;
  }

  protected Object getElement(int index) {
    Object element = null;

    List items = itemManager.getItems();

    boolean done = false;

    for(int i=0; i < items.size() && !done; i++) {
      Item item = (Item)items.get(i);
      if(item.getId().intValue() == index) {
        element = item;
        done = true;
      }
    }

    return element;
  }

  protected int indexOf(Object object) {
    int index = -1;

    List items = itemManager.getItems();

    Item element = (Item)object;

    boolean done = false;

    for(int i=0; i < items.size() && !done; i++) {
      Item item = (Item)items.get(i);
      if(item.getId().intValue() == element.getId().intValue()) {
        index = i;
        done = true;
      }
    }

    return index;
  }

}




