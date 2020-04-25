package com.sdaacademy.expert_in_action.repository;

import com.sdaacademy.expert_in_action.model.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PasswordsRepository extends JpaRepository<PasswordHistory, UUID> {
    List<PasswordHistory> findAllByUser_UserId(UUID userId);
//    PasswordHistory findPasswordHistory(UUID userId);

}
