package com.sdaacademy.expert_in_action.service;

import com.sdaacademy.expert_in_action.model.PasswordHistory;
import com.sdaacademy.expert_in_action.model.Role;
import com.sdaacademy.expert_in_action.model.User;
import com.sdaacademy.expert_in_action.repository.RoleRepository;
import com.sdaacademy.expert_in_action.repository.UserRepositor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepositor userRepositor;
    private RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepositor userRepositor, RoleRepository roleRepository) {
        this.userRepositor = userRepositor;
        this.roleRepository = roleRepository;

    }

    public Optional<User> getUserById(UUID user_id) {
        return userRepositor.findById(user_id);
    }

    public Optional<User> getUserByLogin(String user_id) {
        return userRepositor.findUserByLogin(user_id);
    }

    public List<User> getAllUser() {
        return userRepositor.findAll();
    }

    public boolean registerUser(User user) {

        if (userRepositor.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())) ||
                userRepositor.findAll().stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {
            return false;
        } else {
            user.setUserId(UUID.randomUUID());
            user.setRole(roleRepository.findRoleByRoleName("ROLE_USER"));
            userRepositor.save(user);
//            PasswordHistory password1 = new PasswordHistory(user.getPassword(), user);
//            password1.setPasswordId(UUID.randomUUID());
//            password1.setCreatePasswordDate(LocalDateTime.now());
//            passwordRepository.save(password1);
            return true;
        }
    }

    public Boolean deleteUser(UUID uuid) {
        Optional<User> optUser = userRepositor.findById(uuid);
        if (optUser.isPresent()) {
            User user = optUser.get();
            userRepositor.delete(user);
            return true;
        }
        return false;
    }

    public Boolean updateStatus(UUID userId, Boolean status) {
        Optional<User> optUser = userRepositor.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setStatus(status);
            userRepositor.save(user);
            return true;

        }
        return false;
    }

    public Boolean updatePassword(UUID userId, String newPassword, String confirmPassword) {
        Optional<User> userOpt = userRepositor.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.getPassword().equals(newPassword) || newPassword.equals(confirmPassword)) {
                user.setPassword(newPassword);
                userRepositor.save(user);
                return true;
            }
            return false;
        }
        return false;
    }
public Boolean updateRole(UUID userID,String roleName){
    Optional<User> optUser = userRepositor.findById(userID);
    if (optUser.isPresent()) {
        User user = optUser.get();
        user.setRole(roleRepository.findRoleByRoleName("ROLE_USER"));
        userRepositor.save(user);
        return true;

    }
    return false;
}

//    public List<PasswordHistory> passwordList(UUID userId) {
//        Optional<User> userOpt = userRepositor.findById(userId);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            return passwordRepository.findPasswordHistoryByUser(userId);
//        }
//        return null;
//    }

}
