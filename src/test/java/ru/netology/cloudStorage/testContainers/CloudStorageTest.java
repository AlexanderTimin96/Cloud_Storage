package ru.netology.cloudStorage.testContainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.cloudStorage.DTO.UserDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudStorageTest {

    private static final int PORT = 8081;
    private static final String LOGIN = "test1@test.ru";
    private static final String PASSWORD = "test1";

    @Autowired
    public TestRestTemplate restTemplate;
    private UserDTO userDTO;

    @Container
    public static PostgreSQLContainer postgreSQLContainer
            = PostgreSQL_Container.getInstance();

    @Container
    public static GenericContainer<?> app = new GenericContainer<>("app:latest")
            .withExposedPorts(PORT)
            .dependsOn(postgreSQLContainer);

    @BeforeEach
    public void init() {
        userDTO = UserDTO.builder()
                .login(LOGIN).password(PASSWORD).build();
    }

    @Test
    void createUserTest_ThenSuccess() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI url = new URI("http://" + app.getHost() + ":" + PORT + "/users/create");
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity(url, requestEntity, UserDTO.class);
        Assertions.assertEquals(LOGIN, Objects.requireNonNull(responseEntity.getBody()).getLogin());
    }

    @Test
    void loginAppTest_ThenSuccess() {
        String getLoginURI = "http://" + app.getHost() + ":" + PORT + "/login";
        String authToken = restTemplate.postForObject(getLoginURI, userDTO, String.class);
        Assertions.assertNotNull(authToken);
    }
}