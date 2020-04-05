package com.sdaacademy.expert_in_action.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    private UUID userId;

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String token;
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    private LocalDateTime registrationDate;
    private LocalDateTime LastLoginDate;
    private LocalDateTime failLoginDate;
    private LocalDate tokenDate;
    private Boolean status;


//    token_resetu_hasla          VARCHAR2(64)       NULL,
//    waznosc_tokenu_resetu_hasla TIMESTAMP(6)       NULL,
//    idfirma                     CHAR(32)           NOT NULL


}

