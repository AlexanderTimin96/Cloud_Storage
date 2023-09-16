package ru.netology.cloudStorage.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.cloudStorage.entity.user.UsersEntity;
import ru.netology.cloudStorage.repository.UserRepository;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UsersEntity userDetails = userRepository.findByLogin(login).orElseThrow(
                () ->
                        new UsernameNotFoundException(
                                format("User with login - %s, not found", login)));
        return userDetails;
    }
}