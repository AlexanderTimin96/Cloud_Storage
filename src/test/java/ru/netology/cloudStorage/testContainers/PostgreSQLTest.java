package ru.netology.cloudStorage.testContainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.controller.UserController;
import ru.netology.cloudStorage.entity.user.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PostgreSQLTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer
            = PostgreSQL_Container.getInstance();

    @Autowired
    private UserController userController;

    private UserDTO userDTO;

    @BeforeEach
    public void init() {
        userDTO = UserDTO.builder()
                .login("test@test.ru")
                .password("test")
                .build();
    }

    @Test
    @Rollback
    public void WhenCreateUserExpectHttpStatus() {
        System.out.println(postgreSQLContainer.getDatabaseName());
        ResponseEntity<User> responseEntity = userController.createUser(userDTO);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        User entityBody = responseEntity.getBody();

        Assertions.assertEquals("test@test.ru", entityBody.getLogin());
    }
}