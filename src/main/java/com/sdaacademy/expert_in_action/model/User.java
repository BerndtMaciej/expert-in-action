package com.sdaacademy.expert_in_action.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(length = 16)
    @GeneratedValue
    private UUID userId;
//    @NotBlank
//    @Size(min = 3, max = 255)
    private String name;
//    @NotBlank
//    @Size(min = 3, max = 255)
    private String lastName;
//    @Email
//    @NotBlank
    private String email;
//    @NotBlank
//    @Size(min = 6, max = 255)
    private String password;
//    @NotBlank
    private String city;
    private String token;
//    @NotBlank
    private String login;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private LocalDateTime registrationDate;
    private LocalDateTime LastLoginDate;
    private LocalDateTime failLoginDate;
    private LocalDate tokenDate;
    private Boolean status;


//    @OneToMany(mappedBy = "user")
//    private List<PasswordHistory> passwords;

    public User(String name, String lastName, String login, String email, String password, String city, LocalDateTime registrationDate, Boolean status) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.city = city;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;


    }

    public User(UUID user_id, String city, String email, String name, String lastName, String login, String password, Boolean status) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.city = city;
        this.password = password;
        this.status = status;

    }
}

