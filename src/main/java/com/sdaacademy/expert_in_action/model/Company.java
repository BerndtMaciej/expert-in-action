package com.sdaacademy.expert_in_action.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {
    @Id
    private UUID companyId;
    private String coampanyName;
    private String city;
    private String comopanyAddress;
    private int postalCode;
    private int nip;
    @ManyToMany
    @JoinTable(
            name = "comapny_comapanyType",
            joinColumns = @JoinColumn(name = "companyId"),
            inverseJoinColumns = @JoinColumn(name = "companyTypeId")
    )
    private Set<CompanyType> companyType;
    @ManyToMany
    @JoinTable(
            name = "comapny_service",
            joinColumns = @JoinColumn(name = "companyId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId")
    )
    private Set<Services> service;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "company")
    private List<Offer> offers;


}
