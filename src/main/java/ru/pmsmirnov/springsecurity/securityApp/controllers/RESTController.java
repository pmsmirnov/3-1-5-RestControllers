package ru.pmsmirnov.springsecurity.securityApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.services.RoleService;

import java.util.List;

@Controller
@RequestMapping("/api")
public class RESTController {

    private final RoleService roleService;

    public RESTController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ResponseBody
    @PostMapping("/roles")
    public List<Role> roleList() {
        return roleService.listRoles();
    }

}
