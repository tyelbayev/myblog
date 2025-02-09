package com.example.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/myblog")
    public String home() {
        return "forward:/index.html";
    }
}
