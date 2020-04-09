package com.sdaacademy.expert_in_action.service;

import com.sdaacademy.expert_in_action.model.User;
import com.sdaacademy.expert_in_action.repository.UserRepositor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepositor userRepositor;

    @Autowired
    public UserService(UserRepositor userRepositor) {
        this.userRepositor = userRepositor;
    }

    public Optional<User> getUserById(UUID user_id) {
        return userRepositor.findById(user_id);
    }
//    public Optional<User> geUserByLogin(String login){
//        return userRepositor.findUserByLogin(login);
//    }

    public List<User> getAllUser() {
        return userRepositor.findAll();
    }

    public boolean register(User user) {

        if (userRepositor.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))||
        userRepositor.findAll().stream().anyMatch(u->u.getLogin().equals(user.getLogin()))) {
            return false;
        } else {
            user.setUserId(UUID.randomUUID());
            userRepositor.save(user);
            return true;
        }
    }
}
