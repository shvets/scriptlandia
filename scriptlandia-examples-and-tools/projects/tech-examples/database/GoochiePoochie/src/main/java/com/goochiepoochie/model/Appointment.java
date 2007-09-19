package com.goochiepoochie.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Appointment {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  //private PetOwner petOwner;
  //private Pet pet;
  private Date date;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
