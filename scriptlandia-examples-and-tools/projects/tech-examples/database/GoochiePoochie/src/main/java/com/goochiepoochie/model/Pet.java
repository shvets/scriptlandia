package com.goochiepoochie.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Pet {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String veterinar;

  private String referredBy;

  private String medicalProblems;

  private String breed;

  private String size;

  private String name;

  private String sex;

  private String color;

  private Date birthDate;

  private String clip1;
  private String clip2;
  private String clip3;

  private String specialInstructions;

  private String behavior;

  public String getVeterinar() {
    return veterinar;
  }

  public void setVeterinar(String veterinar) {
    this.veterinar = veterinar;
  }

  public String getReferredBy() {
    return referredBy;
  }

  public void setReferredBy(String referredBy) {
    this.referredBy = referredBy;
  }

  public String getMedicalProblems() {
    return medicalProblems;
  }

  public void setMedicalProblems(String medicalProblems) {
    this.medicalProblems = medicalProblems;
  }

  public String getBreed() {
    return breed;
  }

  public void setBreed(String breed) {
    this.breed = breed;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getClip1() {
    return clip1;
  }

  public void setClip1(String clip1) {
    this.clip1 = clip1;
  }

  public String getClip2() {
    return clip2;
  }

  public void setClip2(String clip2) {
    this.clip2 = clip2;
  }

  public String getClip3() {
    return clip3;
  }

  public void setClip3(String clip3) {
    this.clip3 = clip3;
  }

  public String getSpecialInstructions() {
    return specialInstructions;
  }

  public void setSpecialInstructions(String specialInstructions) {
    this.specialInstructions = specialInstructions;
  }

  public String getBehavior() {
    return behavior;
  }

  public void setBehavior(String behavior) {
    this.behavior = behavior;
  }
}