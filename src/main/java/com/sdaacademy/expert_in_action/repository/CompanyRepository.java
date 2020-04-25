package com.sdaacademy.expert_in_action.repository;


import com.sdaacademy.expert_in_action.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findCompanyByLogin(String login);

    Optional<Company> findCompanyByEmail(String email);

    Optional<Company> findCompanyByNip(int nip);
}
