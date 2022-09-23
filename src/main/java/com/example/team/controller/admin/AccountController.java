package com.example.team.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/account")
public class AccountController {
    @GetMapping("/register")
    public String getRegister() {
        return "/admin/account/register";
    }
}
