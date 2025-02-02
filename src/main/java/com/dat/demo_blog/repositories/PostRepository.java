package com.dat.demo_blog.repositories;

import com.dat.demo_blog.entities.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  // @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.createdAt > :createdBefore")
  List<Post> findByUserIdAndCreatedAtBefore(Long userId, LocalDateTime createdBefore);

  List<Post> findByCreatedAtBefore(LocalDateTime createdBefore);

}
