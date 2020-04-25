package com.sdaacademy.expert_in_action.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {
    @Id
    @Column(length = 16)
    @GeneratedValue
    private UUID companyId;
    private String login;
    private String password;
    private String coampanyName;
    private String city;
    private String comopanyAddress;
    private Integer postalCode;
    private Integer nip;
    private String email;
    @Type(type = "text")
    private String description;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private LocalDateTime registrationDate;
    private LocalDateTime LastLoginDate;
    private LocalDateTime failLoginDate;
    private Boolean status;

    public Company(String login, String password, String coampanyName, String city, String comopanyAddress, Integer postalCode, Integer nip, String email, String description, Boolean status) {
        this.login = login;
        this.password = password;
        this.coampanyName = coampanyName;
        this.city = city;
        this.comopanyAddress = comopanyAddress;
        this.postalCode = postalCode;
        this.nip = nip;
        this.email = email;
        this.description = description;
        this.status = status;
    }
}
