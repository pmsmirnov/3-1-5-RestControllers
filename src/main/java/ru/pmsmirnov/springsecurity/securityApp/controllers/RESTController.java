package ru.pmsmirnov.springsecurity.securityApp.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.pmsmirnov.springsecurity.securityApp.dto.UserDTO;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.models.User;
import ru.pmsmirnov.springsecurity.securityApp.services.RoleService;
import ru.pmsmirnov.springsecurity.securityApp.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final RoleService roleService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public RESTController(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ResponseBody
    @PostMapping("/roles")
    public List<Role> roleList() {
        return roleService.listRoles();
    }


    @ResponseBody
    @PostMapping("/users")
    public List<UserDTO> userList() {
        List<User> userList = userService.listUsers();
        return userList.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @ResponseBody
    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public UserDTO userById(@RequestBody Map<String, Integer> userId) {
        User user = userService.getUserById(userId.get("userId"));
        return new UserDTO(user);
    }

    @ResponseBody
    @PostMapping(value = "/adduser", consumes = "application/json", produces = "application/json")
    public void add(@RequestBody UserDTO userDTO) {
        Set<Role> rolesSet = userDTO.getRolesList().stream().map(r -> "ROLE_" + r)
                .map(r -> roleService.getRoleByName(r)).collect(Collectors.toSet());

        User newUser = UserDTO.userDTOToUser(userDTO, rolesSet, passwordEncoder.encode(userDTO.getPassword()));
        System.out.println(newUser);
        userService.add(newUser);
    }

    @ResponseBody
    @PostMapping(value = "/edit", consumes = "application/json", produces = "application/json")
    public void edit(@RequestBody UserDTO userDTO) {
        Set<Role> rolesSet = userService.getUserById(userDTO.getId()).getRoles();
        rolesSet.addAll(userDTO.getRolesList().stream().map(r -> "ROLE_" + r)
                .map(roleService::getRoleByName).filter(r -> !rolesSet.contains(r)).collect(Collectors.toSet()));
        User editUser = userService.getUserById(userDTO.getId());
        editUser = UserDTO.updateUser(userDTO, editUser, rolesSet, passwordEncoder.encode(userDTO.getPassword()));
        userService.update(editUser);
    }

    @ResponseBody
    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public void delete(@RequestBody UserDTO userDTO) {

        User deletedUser = userService.getUserById(userDTO.getId());
        userService.deleteUser(deletedUser);
    }


}
