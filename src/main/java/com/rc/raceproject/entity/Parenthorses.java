package com.rc.raceproject.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Parenthorses {

  @Id
  private String hrNo;
  private String parentType;
  private String parentNo;
  private String parentName;


  public String getHrNo() {
    return hrNo;
  }

  public void setHrNo(String hrNo) {
    this.hrNo = hrNo;
  }


  public String getParentType() {
    return parentType;
  }

  public void setParentType(String parentType) {
    this.parentType = parentType;
  }


  public String getParentNo() {
    return parentNo;
  }

  public void setParentNo(String parentNo) {
    this.parentNo = parentNo;
  }


  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }
}
