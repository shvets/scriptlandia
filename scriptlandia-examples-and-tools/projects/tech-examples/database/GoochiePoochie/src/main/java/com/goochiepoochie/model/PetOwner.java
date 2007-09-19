package com.goochiepoochie.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class PetOwner {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String firstName;
  private String lastName;

  private String homePhone;
  private String workPhone;
  private String cellPhone;

  private String salutation;

  //@OneToMany
//  private Collection pets;

  public PetOwner(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public PetOwner() {
  }

  public Long getId() {
    return id;
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

  public String getHomePhone() {
    return homePhone;
  }

  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public void setWorkPhone(String workPhone) {
    this.workPhone = workPhone;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public String getSalutation() {
    return salutation;
  }

  public void setSalutation(String salutation) {
    this.salutation = salutation;
  }
/*
  public Collection getPets() {
    return pets;
  }

  public void setPets(Collection pets) {
    this.pets = pets;
  }
*/
}