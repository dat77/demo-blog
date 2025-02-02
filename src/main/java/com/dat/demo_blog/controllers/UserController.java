package com.dat.demo_blog.controllers;

import com.dat.demo_blog.entities.User;
import com.dat.demo_blog.entities.dto.UpdateUserDto;
import com.dat.demo_blog.entities.dto.UserDto;
import com.dat.demo_blog.sevices.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UpdateUserDto>> getAllUsers() {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userService.findAllUsers()
            .stream().map(user -> new UpdateUserDto(user.getUsername(), user.getEmail())).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UpdateUserDto> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(new UpdateUserDto(userService.findUserById(id).getUsername(), userService.findUserById(id).getEmail()));
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody @Valid UserDto userDto) {
    User createdUser = userService.createUser(userDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(createdUser);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id,
      @RequestBody @Valid UpdateUserDto userDto) {
    User updatedUser = userService.updateUser(id, userDto);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
    return ResponseEntity.ok("");
  }

}
