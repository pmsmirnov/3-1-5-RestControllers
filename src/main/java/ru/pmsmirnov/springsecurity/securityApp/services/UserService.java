package ru.pmsmirnov.springsecurity.securityApp.services;

import ru.pmsmirnov.springsecurity.securityApp.models.CrudUser;


import java.util.List;

public interface UserService {
    void add(CrudUser user);

    List<CrudUser> listUsers();

    CrudUser getUserById(int id);

    void update(CrudUser user);

    void deleteUser(CrudUser user);

    CrudUser getCrudUserByName(String nick);

}
