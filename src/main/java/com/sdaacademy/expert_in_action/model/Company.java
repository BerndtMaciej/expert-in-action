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
    @Column(length=16)
    @GeneratedValue
    private UUID companyId;
    private String coampanyName;
    private String city;
    private String comopanyAddress;
    private int postalCode;
    private int nip;

}
