package com.skillspring.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String category;

  @Column(nullable = false)
  private String level;

  @Column(nullable = false)
  private String instructor;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private String duration;

  @Column(nullable = false)
  private Integer lessons;

  @Column(nullable = false)
  private Double rating;

  @Lob
  @Column(nullable = false)
  private String description;

  public Course() {
  }

  public Course(Long id, String title, String category, String level, String instructor,
                Integer price, String duration, Integer lessons, Double rating,
                String description) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.level = level;
    this.instructor = instructor;
    this.price = price;
    this.duration = duration;
    this.lessons = lessons;
    this.rating = rating;
    this.description = description;
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

  public String getInstructor() {
    return instructor;
  }

  public void setInstructor(String instructor) {
    this.instructor = instructor;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
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

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
