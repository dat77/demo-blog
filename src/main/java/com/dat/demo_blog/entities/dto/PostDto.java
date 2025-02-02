package com.dat.demo_blog.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PostDto {

  @NotNull(message = "User Id is required")
  private Long userId;

  @NotBlank(message = "Post could not be empty")
  private String post;

  private LocalDateTime createdAt;

  public PostDto() {
  }

  public PostDto(Long userId, String post, LocalDateTime createdAt) {
    this.userId = userId;
    this.post = post;
    this.createdAt = createdAt;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
