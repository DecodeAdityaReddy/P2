package com.skillspring.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "enrollments",
    uniqueConstraints = @UniqueConstraint(columnNames = {"courseId", "email"})
)
public class Enrollment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long courseId;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String userName;

  @Column(nullable = false)
  private String purchasedAt;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  private Integer progress;

  public Enrollment() {
  }

  public Enrollment(Long id, Long courseId, String email, String userName,
                    String purchasedAt, String status, Integer progress) {
    this.id = id;
    this.courseId = courseId;
    this.email = email;
    this.userName = userName;
    this.purchasedAt = purchasedAt;
    this.status = status;
    this.progress = progress;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPurchasedAt() {
    return purchasedAt;
  }

  public void setPurchasedAt(String purchasedAt) {
    this.purchasedAt = purchasedAt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getProgress() {
    return progress;
  }

  public void setProgress(Integer progress) {
    this.progress = progress;
  }
}
