package com.example.team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class Blog {
    @GetMapping
    public String getBlog() {
        return "blog";
    }
}
