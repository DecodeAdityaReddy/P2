package com.skillspring.lms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCourseRequest {
  @NotBlank
  private String title;
  @NotBlank
  private String category;
  @NotBlank
  private String level;
  @NotBlank
  private String duration;
  @NotNull
  @Min(1)
  private Integer lessons;
  @NotNull
  @Min(0)
  private Integer price;
  @NotBlank
  private String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public Integer getLessons() {
    return lessons;
  }

  public void setLessons(Integer lessons) {
    this.lessons = lessons;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
