package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Item;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;
import validator.ItemValidator;

public class Test7Controller extends AbstractWizardFormController {

  protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                                       Object command, BindException errors) throws Exception {
    Item item = (Item) command;

    logger.info(getClass().getName() + ".processFinish()");

    return new ModelAndView(new RedirectView("test7"));
  }

  protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response,
                                       Object command, BindException errors) throws Exception {
    logger.info(getClass().getName() + ".processCancel()");    

    return new ModelAndView("test7-cancelled");
  }

  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, false));
  }

  protected void validatePage(Object command, Errors errors, int page, boolean finish) {
    ItemValidator validator = (ItemValidator)getValidator();

    if(page == 0) {
      validator.setValidationType(ItemValidator.ValidationType.NAME);
    }
    else if(page == 1) {
      validator.setValidationType(ItemValidator.ValidationType.EXPIRATION_DATE);
    }
    else {
      validator.setValidationType(ItemValidator.ValidationType.ALL);
    }

    validator.validate(command, errors);
  }

  protected int getTargetPage(HttpServletRequest request, int i) {
    return super.getTargetPage(request, i);
  }

}

