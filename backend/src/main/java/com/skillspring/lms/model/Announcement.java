package com.skillspring.lms.model;

public class Announcement {
  private Long id;
  private String title;
  private String audience;

  public Announcement() {
  }

  public Announcement(Long id, String title, String audience) {
    this.id = id;
    this.title = title;
    this.audience = audience;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAudience() {
    return audience;
  }

  public void setAudience(String audience) {
    this.audience = audience;
  }
}
