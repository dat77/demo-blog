package com.dat.demo_blog.assessment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dat.demo_blog.entities.Post;
import com.dat.demo_blog.entities.dto.UpdatePostDto;
import com.dat.demo_blog.repositories.PostRepository;
import com.dat.demo_blog.repositories.UserRepository;
import com.dat.demo_blog.sevices.PostService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

  @InjectMocks
  private PostService postService;

  @Mock
  private PostRepository postRepository;

  @Mock
  private UserRepository userRepository;

  @BeforeAll
  public static void beforeAll() {
    MockitoAnnotations.openMocks(PostServiceTest.class);
  }

  @Test
  public void updatePostTest() {
    UpdatePostDto updatePostDto = new UpdatePostDto();
    updatePostDto.setPost("some updated text for test");
    Long id = 1L;
    Post post = new Post();
    post.setPost("some initial text for test");
    Post updatedPost = new Post();
    updatedPost.setPost(updatePostDto.getPost());

    when(postRepository.findById(id)).thenReturn(Optional.of(post));
    when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

    Post savedPost = postService.updatePost(id, updatePostDto);

    assertNotNull(savedPost);
    assertEquals(updatePostDto.getPost(), savedPost.getPost());
    verify(postRepository, times(1)).findById(id);
    verify(postRepository, times(1)).save(any(Post.class));
  }



}
