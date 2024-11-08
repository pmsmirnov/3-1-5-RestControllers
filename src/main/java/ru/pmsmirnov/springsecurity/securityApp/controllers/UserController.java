package ru.pmsmirnov.springsecurity.securityApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudUser;
import ru.pmsmirnov.springsecurity.securityApp.services.UserService;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String usersPage(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =  (UserDetails)auth.getPrincipal();
        String userName = userDetails.getUsername();
        CrudUser user = userService.getCrudUserByName(userName);
        model.addAttribute("user_", user);
        return "user";
    }
}
