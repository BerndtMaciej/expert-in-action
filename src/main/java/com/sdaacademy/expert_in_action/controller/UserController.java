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

    @GetMapping("/UserPasswords/id={user_id}")
    public List<String> getUserPasswordsByID(@PathVariable("user_id") UUID userId) {

        return userService.passwordList(userId);
    }

    @GetMapping("/User/id={user_id}")
    public User getUserByID(@PathVariable("user_id") UUID userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        return userOpt.orElseGet(User::new);
    }

    @GetMapping("/User/login={login}")
    public User getUserByLogin(@PathVariable("login") String login) {
        Optional<User> userOpt = userService.getUserByLogin(login);
        return userOpt.orElseGet(User::new);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @PostMapping("/registerUser")
    public Boolean registerUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String login, @RequestParam String email, @RequestParam
            String password, @RequestParam String city) {
        return userService.registerUser(new User(name, lastName, login, email, password, city, LocalDateTime.now(), false));

    }

    @DeleteMapping("/delete")
    public Boolean deleteUserByID(@RequestParam UUID uuid) {
        return userService.deleteUser(uuid);
    }

    @PutMapping("/updateUserStatus")
    public Boolean updateStatus(@RequestParam UUID userId, @RequestParam Boolean status) {
        return userService.updateStatus(userId, status);
    }

    @PutMapping("/updateUserPassword")
    public Boolean updatePassword(@RequestParam("user_id") UUID userId, @RequestParam("password") String password, @RequestParam("confirmpassword") String confirmPassword) {
        return userService.updatePassword(userId, password, confirmPassword);
    }

    @PutMapping("/updateUserLogin")
    public Boolean updateLogin(@RequestParam("user_id") UUID userId, @RequestParam("login") String login) {
        return userService.updateLogin(userId, login);
    }

    @PutMapping("/upadateUserRole")
    public Boolean updateUserRole(@RequestParam("user_id") UUID userId, @RequestParam("role") String role) {
        return userService.updateRole(userId, role);
    }

    @PutMapping("/upadateUser")
    public Boolean updateUser(@RequestParam UUID userId, String name, String lastName, String login, String email,
                              String password, String confirmPassword, String city) {
        return userService.updateUser(userId, name, lastName, login, email, password, confirmPassword, city);
    }

}

