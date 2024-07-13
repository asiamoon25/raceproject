package com.rc.raceproject.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;


@Entity
@ToString
public class Horses {
  @Id
  @Column(name = "hrNo")
  private String hrNo;
  @Column(name = "hrName")
  private String hrName;
  @Column(name = "sex")
  private String sex;
  @Column(name = "birthday")
  private String birthday;
  @Column(name = "rank")
  private String rank;
  @Column(name = "rating")
  private int rating;
  @Column(name = "hrLastAmt")
  private String hrLastAmt;
  @Column(name = "meet")
  private String meet;
  @Column(name = "name")
  private String name;


  public String getHrNo() {
    return hrNo;
  }

  public void setHrNo(String hrNo) {
    this.hrNo = hrNo;
  }


  public String getHrName() {
    return hrName;
  }

  public void setHrName(String hrName) {
    this.hrName = hrName;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }


  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }


  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }


  public String getHrLastAmt() {
    return hrLastAmt;
  }

  public void setHrLastAmt(String hrLastAmt) {
    this.hrLastAmt = hrLastAmt;
  }


  public String getMeet() {
    return meet;
  }

  public void setMeet(String meet) {
    this.meet = meet;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
