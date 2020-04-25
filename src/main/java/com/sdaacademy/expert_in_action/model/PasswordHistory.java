package com.sdaacademy.expert_in_action.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordHistory {

    @Id
    @Column(length = 16)
    @GeneratedValue
    private UUID passwordId;
    private String password;
    private LocalDateTime createPasswordDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public PasswordHistory(String password, User user) {

        this.password = password;
        this.user = user;
    }

    public PasswordHistory(String password, Company company) {

        this.password = password;
        this.company = company;
    }

}
