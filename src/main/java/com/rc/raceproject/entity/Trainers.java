package com.rc.raceproject.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Trainers {
  @Id
  private String trNo;
  private String trName;


  public String getTrNo() {
    return trNo;
  }

  public void setTrNo(String trNo) {
    this.trNo = trNo;
  }


  public String getTrName() {
    return trName;
  }

  public void setTrName(String trName) {
    this.trName = trName;
  }

}
