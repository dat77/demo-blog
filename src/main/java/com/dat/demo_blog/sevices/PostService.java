package com.dat.demo_blog.sevices;

import com.dat.demo_blog.entities.Post;
import com.dat.demo_blog.entities.User;
import com.dat.demo_blog.entities.dto.PostDto;
import com.dat.demo_blog.entities.dto.UpdatePostDto;
import com.dat.demo_blog.exceptions.IdNotFoundException;
import com.dat.demo_blog.repositories.PostRepository;
import com.dat.demo_blog.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;

  private final UserRepository userRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public Post findPostById(Long id) {
    Optional<Post> existingPostOptional = postRepository.findById(id);
    return existingPostOptional
        .orElseThrow(() -> new IdNotFoundException("Post cannot be found"));
  }

  public Post createPost(PostDto postDto) {
    Optional<User> existingUserOptional = userRepository.findById(postDto.getUserId());
    if (!existingUserOptional.isPresent()) {
      throw new IdNotFoundException("Post for non-existing user could not be created");
    }
    Post post = new Post();
    post.setUser(existingUserOptional.get());
    post.setPost(postDto.getPost());
    return postRepository.save(post);
  }

  public Set<Post> findPostsByUserId(Long userId){
    Optional<User> existingUserOptional = userRepository.findById(userId);
    if (!existingUserOptional.isPresent()) {
      throw new IdNotFoundException("User cannot be found");
    }
    return existingUserOptional.get().getPosts();
  }

  public List<Post> findPostsByUserIdAndDateBefore(Long userId, LocalDateTime createdBefore) {
    return postRepository.findByUserIdAndCreatedAtBefore(userId, createdBefore);
  }

  public List<Post> findPostsByDateBefore(LocalDateTime createdBefore) {
    return postRepository.findByCreatedAtBefore(createdBefore);
  }

  public Post updatePost(Long id, UpdatePostDto updatePostDto){
    Optional<Post> optionalPost = postRepository.findById(id);
    if (!optionalPost.isPresent()) {
      throw new IdNotFoundException("Post not found");
    }
    Post post = optionalPost.get();
    post.setPost(updatePostDto.getPost());
    return postRepository.save(post);
  }

  public void deletePost(Long postId){
    postRepository.deleteById(postId);
  }


}
