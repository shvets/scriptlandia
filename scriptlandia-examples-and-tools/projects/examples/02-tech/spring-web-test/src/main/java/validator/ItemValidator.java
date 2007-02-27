package validator;

import java.util.Date;

import data.Item;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ItemValidator implements Validator {
  public enum ValidationType { ALL, NAME, EXPIRATION_DATE, PRICE}

  private ValidationType validationType = ValidationType.ALL;

  private int minNameLength = 3;

  public boolean supports(Class clazz) {
    return Item.class.isAssignableFrom(clazz);
  }

  public void validate(Object obj, Errors errors) {
    Item item = (Item) obj;

    if(validationType == ValidationType.ALL || validationType == ValidationType.NAME) {
      validateName(item, errors);
    }

    if(validationType == ValidationType.ALL || validationType == ValidationType.EXPIRATION_DATE) {
      validateExpirationDate(item, errors);
    }

    if(validationType == ValidationType.ALL || validationType == ValidationType.PRICE) {
      validatePrice(item, errors);
    }
  }

  private void validateName(Item item, Errors errors) {
    String name = item.getName();

    if (name == null || name.length() == 0) {
      errors.rejectValue("name", "error.required");
    }
    else if(name.length() < getMinNameLength()) {
      errors.rejectValue("name", "error.name.too.short",
                          new Object[] {new Integer(getMinNameLength())}, "Name is too short.");
    }
  }

  private void validateExpirationDate(Item item, Errors errors) {
    Date expirationDate = item.getExpirationDate();

    if (expirationDate == null) {
      errors.rejectValue("expirationDate", "error.required");
    }
  }

  private void validatePrice(Item item, Errors errors) {
    Double price = item.getPrice();

    if (price == null) {
      errors.rejectValue("price", "error.required");
    }
    else if(price.longValue() < 0) {
      errors.rejectValue("price", "error.price.cannot.be.negative");
    }
  }

  public int getMinNameLength() {
    return minNameLength;
  }

  public void setMinNameLength(int minNameLength) {
    this.minNameLength = minNameLength;
  }

  public ValidationType getValidationType() {
    return validationType;
  }

  public void setValidationType(ValidationType validationType) {
    this.validationType = validationType;
  }

}
