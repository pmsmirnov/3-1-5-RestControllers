package ru.pmsmirnov.springsecurity.securityApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.models.User;
import ru.pmsmirnov.springsecurity.securityApp.services.RoleService;
import ru.pmsmirnov.springsecurity.securityApp.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder pe) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = pe;
    }

    @GetMapping()
    public String usersPage(HttpServletRequest request, ModelMap model) {
        List<User> uList = userService.listUsers();
        if (request.getParameter("count") == null) {
            model.addAttribute("users___", uList);
            return "admin";
        }
        int quantity = Integer.parseInt(request.getParameter("count"));
        List<User> outList = uList.stream().limit(quantity).toList();
        model.addAttribute("users___", outList);
        return "admin";
    }

    @GetMapping(value = "/adduser")
    public String addPage(ModelMap model) {
        List<Role> rList = roleService.listRoles();
        List<String> rSList = rList.stream().map(Role::getRoleName).toList();
        model.addAttribute("roles___", rSList);
        return "adduser";
    }

    @PostMapping(value = "/add")
    public String add(User newUser, @RequestParam("roleFromForm") String roleFromForm) {
        Role role = roleService.getRoleByName(roleFromForm);
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(role);
        newUser.setRoles(rolesSet);
        newUser.setPasswd(passwordEncoder.encode(newUser.getPassword()));
        userService.add(newUser);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String editPage(@RequestParam(value = "userId") int uId, ModelMap model) {
        User user = userService.getUserById(uId);
        List<String> roles = roleService.listRoles().stream().map(x -> x.getRoleName()).toList();
        model.addAttribute("user_", user);
        model.addAttribute("roles_", roles);
        return "edit";
    }

    @PostMapping(value = "/edit")
    public String edit(User editedUser, @RequestParam("roleFromForm") String roleFromForm) {
        Set<Role> rolesSet = userService.getUserById(editedUser.getId()).getRoles();
        Role role = roleService.getRoleByName(roleFromForm);
        rolesSet.add(role);
        editedUser.setRoles(rolesSet);
        editedUser.setPasswd(passwordEncoder.encode(editedUser.getPassword()));
        userService.update(editedUser);
        return "redirect:/admin";
    }

    @GetMapping(value = "/delete")
    public String editPage(@RequestParam(value = "userId") int uId) {
        userService.deleteUser(userService.getUserById(uId));
        return "redirect:/admin";
    }

}
