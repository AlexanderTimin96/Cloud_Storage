package ru.netology.cloudStorage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.entity.user.User;
import ru.netology.cloudStorage.entity.user.UserRole;
import ru.netology.cloudStorage.repository.UserRepository;
import ru.netology.cloudStorage.service.user.UserService;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void init() {
        userDTO = UserDTO.builder()
                .login("test@test.ru")
                .password("test")
                .build();
        user = User.builder()
                .login("test@test.ru")
                .password("test")
                .roles(Collections.singleton(UserRole.ROLE_USER))
                .build();
    }

    @Test
    public void createUser_thenSuccess() {
        Mockito.when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> userService.createUser(userDTO));
    }


    @Test
    public void deleteUserByLogin_thenSuccess() {
        Mockito.when(userRepository.findUserByLogin(userDTO.getLogin())).thenReturn(Optional.ofNullable(user));

        userService.deleteUserByLogin(userDTO.getLogin());

        Mockito.verify(userRepository, Mockito.times(2)).findUserByLogin(userDTO.getLogin());
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    public void findUserByLogin_thenSuccess() {
        Mockito.when(userRepository
                .findUserByLogin(userDTO.getLogin())).thenReturn(Optional.ofNullable(user));

        userService.findUserByLogin(userDTO.getLogin());

        Mockito.verify(userRepository,
                Mockito.times(2)).findUserByLogin(userDTO.getLogin());
    }
}
