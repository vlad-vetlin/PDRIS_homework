package com.filesaver.homework2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }
}
