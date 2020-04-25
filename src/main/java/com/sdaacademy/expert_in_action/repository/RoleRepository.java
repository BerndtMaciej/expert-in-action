package com.sdaacademy.expert_in_action.repository;

import com.sdaacademy.expert_in_action.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
Role findRoleByRoleName(String rolename);
}
