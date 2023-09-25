package ru.netology.cloudStorage.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.entity.user.User;
import ru.netology.cloudStorage.exception.UserNotFoundException;
import ru.netology.cloudStorage.model.Token;
import ru.netology.cloudStorage.repository.UserRepository;
import ru.netology.cloudStorage.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Token login(@NonNull UserDTO userDTO) {
        User userFromDatabase = findUserInStorage(userDTO.getLogin());
        if (isEquals(userDTO, userFromDatabase)) {
            String accessToken = jwtProvider.generateAccessToken(userFromDatabase);
            return new Token(accessToken);
        } else {
            throw new UserNotFoundException();
        }
    }

    public String logout(String authToken, HttpServletRequest request,
                         HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = findUserInStorage(auth.getName());
        SecurityContextLogoutHandler securityContextLogoutHandler =
                new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, auth);
        jwtProvider.removeToken(authToken);
        return user.getLogin();
    }

    private boolean isEquals(UserDTO userDTO, User userFromDatabase) {
        return passwordEncoder.matches(userDTO.getPassword(), userFromDatabase.getPassword());
    }

    private User findUserInStorage(String login) {
        if (userRepository.findUserByLogin(login).isPresent()) {
            return userRepository.findUserByLogin(login).get();
        } else {
            throw new UserNotFoundException();
        }
    }
}
