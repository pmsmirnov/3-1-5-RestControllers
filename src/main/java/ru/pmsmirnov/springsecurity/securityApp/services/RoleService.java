package ru.pmsmirnov.springsecurity.securityApp.services;

import ru.pmsmirnov.springsecurity.securityApp.models.CrudRole;
import java.util.List;

public interface RoleService {

    List<CrudRole> listRoles();

    CrudRole getRoleByName(String roleName);
}
