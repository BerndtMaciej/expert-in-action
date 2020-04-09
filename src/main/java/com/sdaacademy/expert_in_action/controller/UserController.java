package com.sdaacademy.expert_in_action.controller;

import com.sdaacademy.expert_in_action.model.User;
import com.sdaacademy.expert_in_action.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/User/id={user_id}")
public User getUserByID(@PathVariable("user_id") UUID user_id){
        Optional<User> userOpt = userService.getUserById(user_id);
        return userOpt.orElseGet(User::new);
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }
    @PostMapping("/register")
    public Boolean register(@RequestParam String name,@RequestParam String lastName,@RequestParam String login,@RequestParam String email,@RequestParam
            String password,@RequestParam String city){
        return userService.register(new User(name,lastName,login,email,password,city,LocalDateTime.now(),true));

    }
}

