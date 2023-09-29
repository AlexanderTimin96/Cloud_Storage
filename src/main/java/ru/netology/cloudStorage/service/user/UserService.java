package ru.netology.cloudStorage.service.user;

import ru.netology.cloudStorage.DTO.UserDTO;
import ru.netology.cloudStorage.entity.user.User;

public interface UserService {

    User createUser(UserDTO userDto);

    void deleteUserByLogin(String login);

    User findUserByLogin(String login);
}
