package com.skillspring.lms.model;

public class Payment {
  private String id;
  private Long courseId;
  private String email;
  private Integer amount;
  private String provider;
  private String status;
  private String paidAt;

  public Payment() {
  }

  public Payment(String id, Long courseId, String email, Integer amount,
                 String provider, String status, String paidAt) {
    this.id = id;
    this.courseId = courseId;
    this.email = email;
    this.amount = amount;
    this.provider = provider;
    this.status = status;
    this.paidAt = paidAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(String paidAt) {
    this.paidAt = paidAt;
  }
}
