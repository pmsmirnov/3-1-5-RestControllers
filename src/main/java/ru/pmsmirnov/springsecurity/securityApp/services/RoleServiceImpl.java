package ru.pmsmirnov.springsecurity.securityApp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pmsmirnov.springsecurity.securityApp.dao.CrudRoleDao;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final CrudRoleDao roleDao;

    public RoleServiceImpl(CrudRoleDao role) {
        this.roleDao = role;
    }

    @Transactional(readOnly = true)
    public List<Role> listRoles() {
        return roleDao.findAll();
    }

    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

}
