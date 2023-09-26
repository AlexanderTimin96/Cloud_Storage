package ru.netology.cloudStorage.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.entity.user.User;
import ru.netology.cloudStorage.service.user.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{login}")
    public ResponseEntity<User> findUserByLogin(@NotNull @PathVariable String login) {
        return new ResponseEntity<>(userService.findUserByLogin(login), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{login}")
    public ResponseEntity<Void> deleteUserByLogin(@NotNull @PathVariable String login) {
        userService.deleteUserByLogin(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
