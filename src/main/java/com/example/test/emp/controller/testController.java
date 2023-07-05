package com.example.test.emp.controller;

import com.example.test.emp.dto.testDTO;
import com.example.test.emp.dao.test;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class testController {

    private List<test> users = new ArrayList<>();
    private int id = 0;

    // User 추가
    @PostMapping("/user")
    public String add(@RequestBody testDTO user) {
        users.add(new test(id, user.getNickname(), user.getPassword()));
        id++;
        return "User 추가 완료";
    }

    // User 조회
    @GetMapping("/user/{id}")
    public test findById(@PathVariable int id) {
        return users.get(id);
    }

    // User 전체 조회
    @GetMapping("/users")
    public List<test> findAll() {

        return users;
    }


}
