package ru.pmsmirnov.springsecurity.securityApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.pmsmirnov.springsecurity.securityApp.dto.UserDTO;
import ru.pmsmirnov.springsecurity.securityApp.mapper.UserMapper;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.models.User;
import ru.pmsmirnov.springsecurity.securityApp.services.RoleService;
import ru.pmsmirnov.springsecurity.securityApp.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Set;
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


    @PostMapping(value = "/roles", produces = "application/json")
    public List<Role> roleList() {
        return roleService.listRoles();
    }



    @PostMapping(value ="/users", produces = "application/json")
    public List<UserDTO> userList() {
        List<User> userList = userService.listUsers();
        return userList.stream().map(UserDTO::new).collect(Collectors.toList());
    }


    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public UserDTO userById(@RequestBody Map<String, Integer> userId) {
        User user = userService.getUserById(userId.get("userId"));
        return new UserDTO(user);
    }


    @PostMapping(value = "/adduser", consumes = "application/json")
    public void add(@RequestBody UserDTO userDTO) {
        Set<Role> rolesSet = userDTO.getRolesList().stream().map(r -> "ROLE_" + r)
                .map(roleService::getRoleByName).collect(Collectors.toSet());
        User newUser = new User();
        UserMapper.userDTOToUser(userDTO, newUser, rolesSet, passwordEncoder.encode(userDTO.getPassword()));
        userService.add(newUser);
    }

    @PostMapping(value = "/edit", consumes = "application/json", produces = "application/json")
    public void edit(@RequestBody UserDTO userDTO) {
        Set<Role> rolesSet = userService.getUserById(userDTO.getId()).getRoles();
        rolesSet.addAll(userDTO.getRolesList().stream().map(r -> "ROLE_" + r)
                .map(roleService::getRoleByName).filter(r -> !rolesSet.contains(r)).collect(Collectors.toSet()));
        User editUser = userService.getUserById(userDTO.getId());
        UserMapper.userDTOToUser(userDTO, editUser, rolesSet, passwordEncoder.encode(userDTO.getPassword()));
        userService.update(editUser);
    }


    @PostMapping(value = "/delete", consumes = "application/json")
    public void delete(@RequestBody UserDTO userDTO) {

        User deletedUser = userService.getUserById(userDTO.getId());
        userService.deleteUser(deletedUser);
    }


    @PostMapping(value = "/userself", produces = "application/json")
    public UserDTO userSelf() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =  (UserDetails)auth.getPrincipal();
        String userName = userDetails.getUsername();
        User user = userService.getCrudUserByName(userName);
        return UserMapper.userToUserDTO(user);
    }


    @GetMapping(value = "/userself", produces = "application/json") //для проверки что отдается для неаутентифицированного пользователя
    public UserDTO userSelfGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser")) {
            return UserMapper.GetAnonymousUserDTO();
        }
        UserDetails userDetails =  (UserDetails)auth.getPrincipal();
        String userName = userDetails.getUsername();
        User user = userService.getCrudUserByName(userName);
        return UserMapper.userToUserDTO(user);
    }

}
