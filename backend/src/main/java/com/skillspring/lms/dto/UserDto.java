package com.skillspring.lms.dto;

import com.skillspring.lms.model.Role;
import com.skillspring.lms.model.User;

public class UserDto {
  private Long id;
  private String name;
  private String email;
  private Role role;

  public UserDto(Long id, String name, String email, Role role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
  }

  public static UserDto from(User user) {
    return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Role getRole() {
    return role;
  }
}
