package ru.pmsmirnov.springsecurity.securityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pmsmirnov.springsecurity.securityApp.dao.CrudRoleDao;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudRole;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final CrudRoleDao roleDao;
    @Autowired
    public RoleServiceImpl(CrudRoleDao role) {
        this.roleDao = role;
    }

    @Transactional
    public List<CrudRole> listRoles() {
        return roleDao.findAll();
    }

    @Transactional
    public CrudRole getRoleByName(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

}
