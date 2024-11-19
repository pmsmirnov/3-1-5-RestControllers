package ru.pmsmirnov.springsecurity.securityApp.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    public UserController() {
    }

    @GetMapping()
    public ModelAndView usersPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user.html");
        return modelAndView;
    }
}
