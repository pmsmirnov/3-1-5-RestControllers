package ru.pmsmirnov.springsecurity.securityApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudRole;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudUser;
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

    public AdminController( @Autowired UserService userService,    @Autowired RoleService roleService,
                            @Autowired PasswordEncoder pe) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = pe;
    }


    @GetMapping()
    public String usersPage(HttpServletRequest request, ModelMap model) {
        List<CrudUser> uList = userService.listUsers();
        if (request.getParameter("count") == null) {
            model.addAttribute("users___", uList);
            return "admin";
        }
        int quantity = Integer.parseInt(request.getParameter("count"));
        List<CrudUser> outList = uList.stream().limit(quantity).toList();
        model.addAttribute("users___", outList);
        return "admin";
    }

    @GetMapping(value = "/adduser")
    public String addPage(ModelMap model) {
        List<CrudRole> rList = roleService.listRoles();
        List<String> rSList = rList.stream().map(CrudRole::getRoleName).toList();
        model.addAttribute("roles___", rSList);
        return "adduser";
    }

    @PostMapping(value = "/add")
    public String add(@RequestParam("nick") String nick, @RequestParam("f_n") String fN,
                      @RequestParam("l_n") String lN, @RequestParam("e_m") String eM,
                      @RequestParam("u_pswd") String uP, @RequestParam("role_from_post") String rolePost) {
        CrudUser newUser = new CrudUser();
        newUser.setNickName(nick);
        newUser.setFirstName(fN);
        newUser.setLastName(lN);
        newUser.seteMail(eM);
        newUser.setPass(passwordEncoder.encode(uP));
        CrudRole crudRole = roleService.getRoleByName(rolePost);
        Set<CrudRole> roleSet = new HashSet<>();
        roleSet.add(crudRole);
        newUser.setRoles(roleSet);
        userService.add(newUser);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String editPage(@RequestParam(value = "userId") int uId, ModelMap model) {
        CrudUser user = userService.getUserById(uId);
        List<String> roles = roleService.listRoles().stream().map(x -> x.getRoleName()).toList();
        model.addAttribute("user_", user);
        model.addAttribute("roles_", roles);
        return "edit";
    }

    @PostMapping(value = "/edit")
    public String edit(@RequestParam("id_user") int idU, @RequestParam("nick") String nN,
                       @RequestParam("f_n") String fN, @RequestParam("l_n") String lN,
                       @RequestParam("e_m") String eM, @RequestParam("pswrd") String uP,
                       @RequestParam("role_from_post") String rolePost) {

        CrudRole crudRole = roleService.getRoleByName(rolePost);
        Set<CrudRole> roles = new HashSet<>();
        roles.add(crudRole);
        CrudUser userForUpd = new CrudUser(idU, nN, fN, lN, eM, passwordEncoder.encode(uP), roles);
        userService.update(userForUpd);
        return "redirect:/admin";
    }

    @GetMapping(value = "/delete")
    public String editPage(@RequestParam(value = "userId") int uId) {
        userService.deleteUser(userService.getUserById(uId));
        return "redirect:/admin";
    }

}
