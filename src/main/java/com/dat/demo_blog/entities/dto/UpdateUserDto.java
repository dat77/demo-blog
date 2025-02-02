package com.dat.demo_blog.entities.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserDto {

  @NotBlank(message = "User name is required")
  private String username;

  @Email(message = "Invalid email format")
  private String email;

  public UpdateUserDto() {
  }

  public UpdateUserDto(String username, String email) {
    this.username = username;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
