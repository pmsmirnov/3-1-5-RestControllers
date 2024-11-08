package ru.pmsmirnov.springsecurity.securityApp.services;

import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import java.util.List;

public interface RoleService {

    List<Role> listRoles();

    Role getRoleByName(String roleName);
}
