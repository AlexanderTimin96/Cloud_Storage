package ru.netology.cloudStorage.service.user;

import ru.netology.cloudStorage.DTO.UserDTO;

public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO updateUser(UserDTO userDto, Long id);

    void deleteUserById(Long id);

    UserDTO findUserByLogin(String login);

}
