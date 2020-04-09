package com.sdaacademy.expert_in_action.repository;

import com.sdaacademy.expert_in_action.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositor extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByUserId(UUID uuid);
}
