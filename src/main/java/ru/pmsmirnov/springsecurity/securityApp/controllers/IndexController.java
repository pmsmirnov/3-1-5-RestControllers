package ru.pmsmirnov.springsecurity.securityApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String sayHello(Authentication authentication) {
        return "hello";
    }
}
