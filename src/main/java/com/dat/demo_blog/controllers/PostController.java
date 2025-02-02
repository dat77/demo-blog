package com.dat.demo_blog.controllers;

import com.dat.demo_blog.entities.Post;
import com.dat.demo_blog.entities.dto.PostDto;
import com.dat.demo_blog.entities.dto.UpdatePostDto;
import com.dat.demo_blog.sevices.PostService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/api/post/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(new PostDto(postService.findPostById(id).getUser().getId(),
            postService.findPostById(id).getPost(),
            postService.findPostById(id).getCreatedAt()));
  }

  @PostMapping("/api/post")
  public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
    Post createdPost = postService.createPost(postDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new PostDto(createdPost.getUser().getId(),
            createdPost.getPost(),
            createdPost.getCreatedAt()));
  }

  @GetMapping("/api/post")
  public ResponseEntity<List<PostDto>> getPostsByDateBefore(
      @RequestParam(value = "createdBefore") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdBefore) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(postService.findPostsByDateBefore(createdBefore).stream()
            .map(post -> new PostDto(post.getUser().getId(), post.getPost(), post.getCreatedAt()))
            .toList());
  }

  @GetMapping("api/user/{userId}/post")
  public ResponseEntity<List<PostDto>> getPostsByUserIdAndDateBefore(@PathVariable Long userId,
      @RequestParam(value = "createdBefore", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdBefore) {
    if (createdBefore != null) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(postService.findPostsByUserIdAndDateBefore(userId, createdBefore).stream()
              .map(post -> new PostDto(post.getUser().getId(), post.getPost(), post.getCreatedAt()))
              .toList());
    } else {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(postService.findPostsByUserId(userId).stream()
              .map(post -> new PostDto(post.getUser().getId(), post.getPost(), post.getCreatedAt()))
              .toList());
    }
  }

  @PutMapping("/api/post/{id}")
  public ResponseEntity<PostDto> updatePost(@PathVariable Long id ,@RequestBody @Valid UpdatePostDto updatePostDto) {
    Post updatedPost = postService.updatePost(id, updatePostDto);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body( new PostDto(updatedPost.getUser().getId(), updatePostDto.getPost(), updatedPost.getCreatedAt()));
  }


  @DeleteMapping("/api/post/{id}")
  public ResponseEntity<?> deletePost(@PathVariable Long id){
    postService.deletePost(id);
    return ResponseEntity.ok("");
  }


}
