package com.example.test.api.emp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("api/hello")
    public String test1() {
        return "Hellow, world!";
    }
}
