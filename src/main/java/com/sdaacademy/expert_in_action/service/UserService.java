package com.sdaacademy.expert_in_action.service;

import com.sdaacademy.expert_in_action.model.PasswordHistory;
import com.sdaacademy.expert_in_action.model.User;
import com.sdaacademy.expert_in_action.repository.PasswordsRepository;
import com.sdaacademy.expert_in_action.repository.RoleRepository;
import com.sdaacademy.expert_in_action.repository.UserRepositor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepositor userRepositor;
    private RoleRepository roleRepository;
    private PasswordsRepository passwordsRepository;


    @Autowired
    public UserService(UserRepositor userRepositor, RoleRepository roleRepository, PasswordsRepository passwordsRepository) {
        this.userRepositor = userRepositor;
        this.roleRepository = roleRepository;
        this.passwordsRepository = passwordsRepository;
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepositor.findById(userId);
    }

    public Optional<User> getUserByLogin(String userId) {
        return userRepositor.findUserByLogin(userId);
    }

    public List<User> getAllUser() {
        return userRepositor.findAll();
    }

    public boolean registerUser(User user) {

//        if (userRepositor.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())) ||
//                userRepositor.findAll().stream().anyMatch(u -> u.getLogin().equals(user.getLogin())))
        if(userRepositor.findUserByLogin(user.getLogin()).isPresent()||
                userRepositor.findUserByEmail(user.getEmail()).isPresent()
        )
        {
            return false;
        } else {
            user.setRole(roleRepository.findRoleByRoleName("ROLE_USER"));
            userRepositor.save(user);
            PasswordHistory password1 = new PasswordHistory(user.getPassword(), user);
            password1.setPasswordId(UUID.randomUUID());
            password1.setCreatePasswordDate(LocalDateTime.now());
            passwordsRepository.save(password1);
            return true;
        }
    }

    public Boolean deleteUser(UUID uuid) {
        Optional<User> optUser = userRepositor.findById(uuid);
        if (optUser.isPresent()) {
            User user = optUser.get();
            for (PasswordHistory passwordHistory : passwordsRepository.findAllByUser_UserId(uuid)
                 ) {
                passwordsRepository.delete(passwordHistory);
            }
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
            if (!user.getPassword().equals(newPassword) && newPassword.equals(confirmPassword)) {
                if (passwordsRepository.findAllByUser_UserId(userId).stream().noneMatch(p -> p.getPassword().equals(newPassword))) {
                    user.setPassword(newPassword);
                    userRepositor.save(user);
                    PasswordHistory password1 = new PasswordHistory(newPassword, user);
                    password1.setCreatePasswordDate(LocalDateTime.now());
                    passwordsRepository.save(password1);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public Boolean updateLogin(UUID userId, String login) {
        Optional<User> userOpt = userRepositor.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.getLogin().equals(login)) {
                if (!userRepositor.findUserByLogin(login).isPresent()) {
                    user.setLogin(login);
                    userRepositor.save(user);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public Boolean updateEmail(UUID userId, String email) {
        Optional<User> userOpt = userRepositor.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.getEmail().equals(email)) {
                if (userRepositor.findUserByEmail(email).isPresent()) {
                    return false;
                }else {
                    user.setEmail(email);
                    userRepositor.save(user);
                    return true;
                }

            }
            return false;
        }
        return false;
    }


    public Boolean updateRole(UUID userID, String roleName) {
        Optional<User> optUser = userRepositor.findById(userID);
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (roleRepository.findRoleByRoleName(roleName)!=null) {
                user.setRole(roleRepository.findRoleByRoleName(roleName));
                userRepositor.save(user);
                return true;
            }
        }
        return false;
    }

    public List<String> passwordList(UUID userId) {
        Optional<User> userOpt = userRepositor.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordsRepository.findAllByUser_UserId(userId).stream().map(p -> p.getPassword()).collect(Collectors.toList());
        }
        return null;
    }

    public Boolean updateUser(UUID userId, String name, String lastName, String login, String email, String password, String confirmPassword, String city) {
        if (name == null && lastName == null && login == null && email == null && password == null && city == null) {
            return false;
        }
        Optional<User> userOpt = userRepositor.findById(userId);
        if (userOpt.isPresent()) {

            boolean flagNane = false;
            boolean flagLastName = false;
            boolean flagLogin = false;
            boolean flagEmail = false;
            boolean flagPassword = false;
            boolean flagCity = false;
            User user = userOpt.get();


            if (email == null) {
                flagEmail = true;
            } else {
                flagEmail = updateEmail(userId, email);
            }
            if (login == null) {
                flagLogin = true;
            } else {
                flagLogin = updateLogin(userId, login);
            }
            if (password == null && confirmPassword == null) {
                flagPassword = true;
            } else {
                flagPassword = updatePassword(userId, password, confirmPassword);
            }
            if (name != null) {
                flagNane = true;
                user.setName(name);
            } else {
                flagNane = true;
            }
            if (lastName != null) {
                flagLastName = true;
                user.setLastName(lastName);
            } else {
                flagLastName = true;
            }
            if (city != null) {
                flagCity = true;
                user.setCity(city);
            } else {
                flagCity = true;
            }

            if (true == flagNane == flagLastName == flagLogin == flagEmail == flagPassword == flagCity) {
                userRepositor.save(user);
                return true;
            }


            return false;
        }
        return false;
    }

}
