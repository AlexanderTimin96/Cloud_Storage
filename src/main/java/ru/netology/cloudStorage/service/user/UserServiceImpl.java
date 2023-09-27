package ru.netology.cloudStorage.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.entity.user.User;
import ru.netology.cloudStorage.entity.user.UserRole;
import ru.netology.cloudStorage.exception.UserAlreadyCreatedException;
import ru.netology.cloudStorage.exception.UserNotFoundException;
import ru.netology.cloudStorage.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) {
        if (userRepository.findUserByLogin(userDTO.getLogin()).isPresent()) {
            log.error("User with login: {} already created", userDTO.getLogin());
            throw new UserAlreadyCreatedException("User with login: " + userDTO.getLogin() + " already created", 0);
        }
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setRoles(Collections.singleton(UserRole.ROLE_USER));
        userRepository.save(user);
        log.info("Creating new user: {}", user);
        return user;
    }

    @Override
    public void deleteUserByLogin(String login) {
        User user = findUserByLogin(login);
        userRepository.delete(user);
        log.info("Deleted user by login: {}", login);
    }

    @Override
    public User findUserByLogin(String login) {
        if (userRepository.findUserByLogin(login).isEmpty()) {
            log.error("User not found by login: {}", login);
            throw new UserNotFoundException("User not found by login:" + login, 0);
        }
        return userRepository.findUserByLogin(login).get();
    }
}

