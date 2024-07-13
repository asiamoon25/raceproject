package com.rc.raceproject.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Racestatistics {

  @Id
  private String hrNo;
  private long rcCntT;
  private long ord1CntT;
  private long ord2CntT;
  private long ord3CntT;
  private long rcCntY;
  private long ord1CntY;
  private long ord2CntY;
  private long ord3CntY;
  private BigDecimal chaksunT;


  public String getHrNo() {
    return hrNo;
  }

  public void setHrNo(String hrNo) {
    this.hrNo = hrNo;
  }


  public long getRcCntT() {
    return rcCntT;
  }

  public void setRcCntT(long rcCntT) {
    this.rcCntT = rcCntT;
  }


  public long getOrd1CntT() {
    return ord1CntT;
  }

  public void setOrd1CntT(long ord1CntT) {
    this.ord1CntT = ord1CntT;
  }


  public long getOrd2CntT() {
    return ord2CntT;
  }

  public void setOrd2CntT(long ord2CntT) {
    this.ord2CntT = ord2CntT;
  }


  public long getOrd3CntT() {
    return ord3CntT;
  }

  public void setOrd3CntT(long ord3CntT) {
    this.ord3CntT = ord3CntT;
  }


  public long getRcCntY() {
    return rcCntY;
  }

  public void setRcCntY(long rcCntY) {
    this.rcCntY = rcCntY;
  }


  public long getOrd1CntY() {
    return ord1CntY;
  }

  public void setOrd1CntY(long ord1CntY) {
    this.ord1CntY = ord1CntY;
  }


  public long getOrd2CntY() {
    return ord2CntY;
  }

  public void setOrd2CntY(long ord2CntY) {
    this.ord2CntY = ord2CntY;
  }


  public long getOrd3CntY() {
    return ord3CntY;
  }

  public void setOrd3CntY(long ord3CntY) {
    this.ord3CntY = ord3CntY;
  }


  public BigDecimal getChaksunT() {
    return chaksunT;
  }

  public void setChaksunT(BigDecimal chaksunT) {
    this.chaksunT = chaksunT;
  }

}
