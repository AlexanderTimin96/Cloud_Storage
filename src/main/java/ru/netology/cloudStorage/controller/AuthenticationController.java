package ru.netology.cloudStorage.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.model.Token;
import ru.netology.cloudStorage.service.authentication.AuthenticationServiceImpl;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/login")
    public Token login(@RequestBody UserDTO userDTO) {
        return authenticationService.login(userDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken,
                                    HttpServletRequest request, HttpServletResponse response) {
        if (authenticationService.logout(authToken, request, response)) {
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
