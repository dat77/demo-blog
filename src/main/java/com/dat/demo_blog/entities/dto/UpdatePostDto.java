package com.dat.demo_blog.entities.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdatePostDto {

  @NotBlank(message = "Post could not be empty")
  private String post;

  public UpdatePostDto() {
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }
}
