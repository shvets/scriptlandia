package com.goochiepoochie.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PetOwner implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id = (long) -1;

  private String firstName;
  private String lastName;

  private String homePhone;
  private String workPhone;
  private String cellPhone;

  private String salutation;

  @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "petOwner")
  private Set<Pet> pets = new HashSet<Pet>();

  public PetOwner() {}

  public PetOwner(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
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

  public Set<Pet> getPets() {
    return pets;
  }

  public void setPets(Set<Pet> pets) {
    this.pets = pets;
  }

  public void addPet(Pet pet) {
    pet.setPetOwner(this);
    pets.add(pet);
  }

  public final String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
}