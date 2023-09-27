package ru.netology.cloudStorage.service.authentication;

import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.model.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    Token login(UserDTO userDTO);

    boolean logout(String authToken, HttpServletRequest request, HttpServletResponse response);
}
