package com.dat.demo_blog.assessment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dat.demo_blog.entities.User;
import com.dat.demo_blog.entities.dto.UserDto;
import com.dat.demo_blog.repositories.UserRepository;
import com.dat.demo_blog.sevices.UserService;
import com.dat.demo_blog.utils.PasswordUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @BeforeAll
  public static void beforeAll() {
    MockitoAnnotations.openMocks(UserServiceTest.class);
  }

  @Test
  public void createUserTest(){
    UserDto userDto = new UserDto("Oscar", "Wilde1", "oscar.wilde@dot.com");
    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(PasswordUtils.hashPassword(userDto.getPassword()));
    user.setEmail(userDto.getEmail());

    when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    User createdUser = userService.createUser(userDto);
    assertNotNull(createdUser);
    assertEquals(userDto.getUsername(), createdUser.getUsername());
    verify(userRepository, times(1)).findByUsername(userDto.getUsername());
    verify(userRepository, times(1)).save(any(User.class));
  }
}
