package com.gyan.darpan.api.controller;

import com.gyan.darpan.dao.users.entity.User;
import com.gyan.darpan.dao.users.repository.UserJpaRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {


    private UserJpaRepository userJpaRepository;

    public UserController(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping(path = "{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserDetail(@PathVariable("userId") Long userId) {
        return userJpaRepository.findById(userId).orElse(null);
    }
}
