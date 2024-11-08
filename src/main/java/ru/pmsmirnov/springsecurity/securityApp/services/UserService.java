package ru.pmsmirnov.springsecurity.securityApp.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.pmsmirnov.springsecurity.securityApp.models.User;


import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    List<User> listUsers();

    User getUserById(int id);

    void update(User user);

    void deleteUser(User user);

    User getCrudUserByName(String nick);

}
