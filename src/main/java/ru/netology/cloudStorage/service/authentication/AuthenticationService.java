package ru.netology.cloudStorage.service.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.model.Token;

public interface AuthenticationService {

    Token login(UserDTO userDTO);

    boolean logout(String authToken, HttpServletRequest request, HttpServletResponse response);
}
