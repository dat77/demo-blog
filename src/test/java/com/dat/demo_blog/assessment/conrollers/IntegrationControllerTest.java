package com.dat.demo_blog.assessment.conrollers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dat.demo_blog.entities.User;
import com.dat.demo_blog.entities.dto.PostDto;
import com.dat.demo_blog.entities.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class IntegrationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void createUserTest() throws Exception {
    UserDto userDto = new UserDto("Oscar", "Wilde1", "oscar.wilde@dot.com");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("Oscar"))
        .andExpect(jsonPath("$.email").value("oscar.wilde@dot.com"));
  }

  @Test
  public void createPostTest() throws Exception {
    UserDto userDto = new UserDto("Agatha", "5Christie", "agatha@dot.com");
    MvcResult mvcResult =mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    User createdUser = objectMapper.readValue(responseBody, User.class);

    String postText = "Text for test post";
    PostDto postDto = new PostDto(createdUser.getId(), postText, LocalDateTime.now());
    mockMvc.perform(post("/api/post")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userId").value(createdUser.getId()))
        .andExpect(jsonPath("$.post").value(postText));
  }

}
