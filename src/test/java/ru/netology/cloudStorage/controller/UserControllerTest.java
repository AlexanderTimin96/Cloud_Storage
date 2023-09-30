package ru.netology.cloudStorage.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.entity.user.User;
import ru.netology.cloudStorage.service.user.UserService;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String AUTH_TOKEN = "auth-token";
    private static final String VALUE_TOKEN = "Bearer ***anyToken***";
    private static final String LOGIN = "test@test.com";
    private static final String PASSWORD = "test";

    MockMvc mockMvc;
    ObjectMapper objectMapper;
    UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new UserController(userService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateUser_thenSuccess() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .login(LOGIN).password(PASSWORD).build();

        User user = User.builder()
                .login(LOGIN).password(PASSWORD).build();

        Mockito.when(userService.createUser(userDTO)).thenReturn(user);

        mockMvc.perform(post("/users/create")
                        .header(AUTH_TOKEN, VALUE_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }


    @Test
    void testFindUserByLogin_thenSuccess() throws Exception {
        User user = User.builder()
                .login(LOGIN).password(PASSWORD).build();
        Mockito.when(userService.findUserByLogin(LOGIN)).thenReturn(user);

        mockMvc.perform(get("/users/{login}", LOGIN)
                        .header(AUTH_TOKEN, VALUE_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void testDeleteUserById_thenSuccess() throws Exception {
        mockMvc.perform(delete("/users/delete/{id}", "2")
                        .header(AUTH_TOKEN, VALUE_TOKEN))
                .andExpect(status().isOk());
    }
}
