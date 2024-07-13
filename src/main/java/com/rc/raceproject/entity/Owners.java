package com.rc.raceproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Owners {

  @Id
  private String owNo;
  private String owName;


  public String getOwNo() {
    return owNo;
  }

  public void setOwNo(String owNo) {
    this.owNo = owNo;
  }


  public String getOwName() {
    return owName;
  }

  public void setOwName(String owName) {
    this.owName = owName;
  }

}
