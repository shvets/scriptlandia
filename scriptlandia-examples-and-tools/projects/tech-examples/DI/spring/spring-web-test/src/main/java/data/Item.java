package data;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
  private static final long serialVersionUID = -8406603909819457684L;

  private Integer id;
  private String name;
  private Double price;
  private Date expirationDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(Double d) {
    price = d;
  }

  public Double getPrice() {
    return price;
  }

  public Date getExpirationDate() {
    return (expirationDate == null) ? expirationDate : (Date)expirationDate.clone();
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = (expirationDate == null) ? expirationDate : (Date)expirationDate.clone();
  }

}
