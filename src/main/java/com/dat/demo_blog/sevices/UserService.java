package com.dat.demo_blog.sevices;

import com.dat.demo_blog.entities.User;
import com.dat.demo_blog.entities.dto.UpdateUserDto;
import com.dat.demo_blog.entities.dto.UserDto;
import com.dat.demo_blog.exceptions.AlreadyExistsException;
import com.dat.demo_blog.repositories.UserRepository;
import com.dat.demo_blog.exceptions.IdNotFoundException;
import com.dat.demo_blog.utils.PasswordUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User findUserById(Long id) {
    Optional<User> existingUserOptional = userRepository.findById(id);
    return existingUserOptional
        .orElseThrow(() -> new IdNotFoundException("The user cannot be found"));
  }

  public User createUser(UserDto userDto) {
    Optional<User> existingUserOptional = userRepository.findByUsername(userDto.getUsername());
    if (existingUserOptional.isPresent()) {
      throw new AlreadyExistsException("The user with such name already exists");
    }
    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(PasswordUtils.hashPassword(userDto.getPassword()));
    user.setEmail(userDto.getEmail());
    return userRepository.save(user);
  }

  public User updateUser(Long id, UpdateUserDto userDto) {
    Optional<User> existingUserOptional = userRepository.findById(id);
    if (!existingUserOptional.isPresent()) {
      throw new IdNotFoundException("The user cannot be found");
    }
    Optional<User> existingUserWithSuchName = userRepository.findByUsername(userDto.getUsername());
    if (existingUserWithSuchName.isPresent()) {
      throw new AlreadyExistsException("The user with such name already exists");
    }
    User existingUser = existingUserOptional.get();
    existingUser.setUsername(userDto.getUsername());
    existingUser.setEmail(userDto.getEmail());
    return userRepository.save(existingUser);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }


}
