package business;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.json.JSONObject;
import org.json.JSONException;

public class User {
  private long id;
  private String title;
  private String firstName;
  private String lastName;
  private boolean admin;
  private String role;

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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
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

	public String toJSONString() throws JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", new Long(this.id));
		jsonObj.put("title", this.title);
		jsonObj.put("firstName", this.firstName);
		jsonObj.put("lastName", this.lastName);
		jsonObj.put("admin", this.admin);
		jsonObj.put("role", this.role);

    return jsonObj.toString();
	}
  
}
