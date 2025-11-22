package com.gyan.darpan.api.controller;

import com.gyan.darpan.dao.users.entity.User;
import com.gyan.darpan.dao.users.repository.UserJdbcRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {


    private UserJdbcRepository userJdbcRepository;

    public UserController(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    @GetMapping(path = "{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserDetail(@PathVariable("userId") Long userId) {
        return userJdbcRepository.findById(userId);
    }
}
