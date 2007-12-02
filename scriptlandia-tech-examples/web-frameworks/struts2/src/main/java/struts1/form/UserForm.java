package struts1.form;

import org.apache.struts.action.ActionForm;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserForm extends ActionForm {
  private long id;
  private String title;
  private String firstName;
  private String lastName;
  private boolean admin;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  /**
   * Equals.
   *
   * @param obj the object
   * @return the boolean
   */
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * To string.
   *
   * @return the string
   */
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
