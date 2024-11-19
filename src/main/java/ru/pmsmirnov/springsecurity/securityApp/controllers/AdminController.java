package ru.pmsmirnov.springsecurity.securityApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    public AdminController() {
    }

    @GetMapping()
    public ModelAndView usersPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html");
        return modelAndView;
    }

    @GetMapping(value = "/adduser")
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adduser.html");
        return modelAndView;
    }
}
