package com.sdaacademy.expert_in_action.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(length=16)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID userId;

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String token;
    private String login;
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

    @OneToMany(mappedBy = "user")
    private List<Company> comapny;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    public User(String name, String lastName,String login, String email, String password,String city, LocalDateTime registrationDate, Boolean status ) {
        this.name = name;
        this.lastName = lastName;
        this.login=login;
        this.email = email;
        this.city= city;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;

    }
}

