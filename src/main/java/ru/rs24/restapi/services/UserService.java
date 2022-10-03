package ru.rs24.restapi.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.rs24.restapi.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    UserDetails loadUserByUsername(String username);
}
