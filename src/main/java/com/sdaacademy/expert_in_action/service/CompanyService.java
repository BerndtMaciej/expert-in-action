package com.sdaacademy.expert_in_action.service;

import com.sdaacademy.expert_in_action.model.Company;
import com.sdaacademy.expert_in_action.model.PasswordHistory;
import com.sdaacademy.expert_in_action.repository.CompanyRepository;
import com.sdaacademy.expert_in_action.repository.PasswordsRepository;
import com.sdaacademy.expert_in_action.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private PasswordsRepository passwordsRepository;
    private RoleRepository roleRepository;


    @Autowired
    public CompanyService(CompanyRepository companyRepository, PasswordsRepository passwordsRepository, RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.passwordsRepository = passwordsRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<Company> getComapnyById(UUID companyId) {
        return companyRepository.findById(companyId);
    }

    public Optional<Company> getCompanyByLogin(String login) {
        return companyRepository.findCompanyByLogin(login);
    }

    public List<Company> getAllComapny() {
        return companyRepository.findAll();
    }

    public Boolean registerComapny(Company company) {
        if (companyRepository.findCompanyByLogin(company.getLogin()).isPresent() ||
                companyRepository.findCompanyByEmail(company.getEmail()).isPresent() ||
                companyRepository.findCompanyByNip(company.getNip()).isPresent()) {
            return false;
        } else {
            company.setRole(roleRepository.findRoleByRoleName("ROLE_COMPANY"));
            companyRepository.save(company);
            PasswordHistory password1 = new PasswordHistory(company.getPassword(), company);
            password1.setPasswordId(UUID.randomUUID());
            password1.setCreatePasswordDate(LocalDateTime.now());
            passwordsRepository.save(password1);
            return true;
        }
    }

    public Boolean deleteComapny(UUID uuid) {
        Optional<Company> optComapny = companyRepository.findById(uuid);
        if (optComapny.isPresent()) {
            Company company = optComapny.get();
            for (PasswordHistory passwordHistory : passwordsRepository.findAllByCompany_CompanyId(uuid)
            ) {
                passwordsRepository.delete(passwordHistory);
            }
            companyRepository.delete(company);
            return true;
        }
        return false;
    }

    public Boolean updateStatus(UUID comapnyId, Boolean status) {
        Optional<Company> comapnyOpt = companyRepository.findById(comapnyId);
        if (comapnyOpt.isPresent()) {
            Company company = comapnyOpt.get();
            company.setStatus(status);
            companyRepository.save(company);
            return true;

        }
        return false;
    }

    public Boolean updatePassword(UUID companyId, String newPassword, String confirmPassword) {
        Optional<Company> comapnyOpt = companyRepository.findById(companyId);
        if (comapnyOpt.isPresent()) {
            Company company = comapnyOpt.get();
            if (!company.getPassword().equals(newPassword) && newPassword.equals(confirmPassword)) {
                if (passwordsRepository.findAllByCompany_CompanyId(companyId).stream().noneMatch(p -> p.getPassword().equals(newPassword))) {
                    company.setPassword(newPassword);
                    companyRepository.save(company);
                    PasswordHistory password1 = new PasswordHistory(newPassword, company);
                    password1.setCreatePasswordDate(LocalDateTime.now());
                    passwordsRepository.save(password1);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public Boolean updateLogin(UUID comapnyId, String login) {
        Optional<Company> comapnyOpt = companyRepository.findById(comapnyId);
        if (comapnyOpt.isPresent()) {
            Company coampny = comapnyOpt.get();
            if (!coampny.getLogin().equals(login)) {
                if (!companyRepository.findCompanyByLogin(login).isPresent()) {
                    coampny.setLogin(login);
                    companyRepository.save(coampny);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public Boolean updateEmail(UUID companyId, String email) {
        Optional<Company> companyOpt = companyRepository.findById(companyId);
        if (companyOpt.isPresent()) {
            Company comapny = companyOpt.get();
            if (!comapny.getEmail().equals(email)) {
                if (companyRepository.findCompanyByEmail(email).isPresent()) {
                    return false;
                } else {
                    comapny.setEmail(email);
                    companyRepository.save(comapny);
                    return true;
                }

            }
            return false;
        }
        return false;
    }

    public Boolean updateNip(UUID companyId, Integer nip) {
        Optional<Company> companyOpt = companyRepository.findById(companyId);
        if (companyOpt.isPresent()) {
            Company comapny = companyOpt.get();
            if (!comapny.getNip().equals(nip)) {
                if (companyRepository.findCompanyByNip(nip).isPresent()) {
                    return false;
                } else {
                    comapny.setNip(nip);
                    companyRepository.save(comapny);
                    return true;
                }

            }
            return false;
        }
        return false;
    }


    public Boolean updateRole(UUID nompanyId, String roleName) {
        Optional<Company> comapnyOpt = companyRepository.findById(nompanyId);
        if (comapnyOpt.isPresent()) {
            Company comapny = comapnyOpt.get();
            if (roleRepository.findRoleByRoleName(roleName) != null) {
                comapny.setRole(roleRepository.findRoleByRoleName(roleName));
                companyRepository.save(comapny);
                return true;
            }
        }
        return false;
    }

    public List<String> passwordList(UUID comapnyId) {
        Optional<Company> coampnyOpt = companyRepository.findById(comapnyId);
        if (coampnyOpt.isPresent()) {
            Company comapny = coampnyOpt.get();
            return passwordsRepository.findAllByCompany_CompanyId(comapnyId).stream().map(p -> p.getPassword()).collect(Collectors.toList());
        }
        return null;
    }

    public Boolean updateComapny(UUID comapnyId, String login, String password, String confirmPassword, String coampanyName, String city, String comopanyAddress, Integer postalCode, Integer nip, String email, String description) {
        if (login == null && password == null && coampanyName == null && city == null && comopanyAddress == null && postalCode == null && nip == null && email == null && description == null) {
            return false;
        }
        Optional<Company> comapnyOpt = companyRepository.findById(comapnyId);
        if (comapnyOpt.isPresent()) {
            boolean flagLogin = false;
            boolean flagPassword = false;
            boolean flagCoampanyName = false;
            boolean flagCity = false;
            boolean flagComopanyAddress = false;
            boolean flagPostalCode = false;
            boolean flagNip = false;
            boolean flagEmail = false;
            boolean flagDescription = false;

            Company company = comapnyOpt.get();


            if (email == null) {
                flagEmail = true;
            } else {
                flagEmail = updateEmail(comapnyId, email);
            }
            if (nip == null) {
                flagNip = true;
            } else {
                flagNip = updateNip(comapnyId, nip);
            }
            if (login == null) {
                flagLogin = true;
            } else {
                flagLogin = updateLogin(comapnyId, login);
            }
            if (password == null && confirmPassword == null) {
                flagPassword = true;
            } else {
                flagPassword = updatePassword(comapnyId, password, confirmPassword);
            }


            if (coampanyName != null) {
                flagCoampanyName = true;
                company.setCoampanyName(coampanyName);
            } else {
                flagCoampanyName = true;
            }
            if (city != null) {
                flagCity = true;
                company.setCity(city);
            } else {
                flagCity = true;
            }
            if (comopanyAddress != null) {
                flagComopanyAddress = true;
                company.setComopanyAddress(comopanyAddress);
            } else {
                flagComopanyAddress = true;
            }
            if (postalCode != null) {
                flagPostalCode = true;
                company.setPostalCode(postalCode);
            } else {
                flagPostalCode = true;
            }
            if (description != null) {
                flagDescription = true;
                company.setDescription(description);
            } else {
                flagDescription = true;
            }


            if (true == flagComopanyAddress == flagCoampanyName == flagDescription == flagLogin == flagEmail == flagPassword == flagCity == flagNip == flagPostalCode) {
                companyRepository.save(company);
                return true;
            }


            return false;
        }
        return false;
    }
}
