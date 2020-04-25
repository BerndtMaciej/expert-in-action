package com.sdaacademy.expert_in_action.controller;

import com.sdaacademy.expert_in_action.model.Company;
import com.sdaacademy.expert_in_action.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CompanyController {
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/ComapnyPasswords/id={company_id}")
    public List<String> getCoampnyPasswordsByID(@PathVariable("company_id") UUID companyId) {

        return companyService.passwordList(companyId);
    }

    @GetMapping("/Company/id={company_id}")
    public Company getComapnyByID(@PathVariable("company_id") UUID comapnyId) {
        Optional<Company> comapnyOpt = companyService.getComapnyById(comapnyId);
        return comapnyOpt.orElseGet(Company::new);
    }

    @GetMapping("/Comapny/login={login}")
    public Company getCompanyByLogin(@PathVariable("login") String login) {
        Optional<Company> comapnyOpt = companyService.getCompanyByLogin(login);
        return comapnyOpt.orElseGet(Company::new);
    }

    @GetMapping("/company")
    public List<Company> getAllComapny() {
        return companyService.getAllComapny();
    }

    @PostMapping("/registerComapny")
    public Boolean registerComapny(String login, String password, String coampanyName, String city, String comopanyAddress, int postalCode, int nip, String email, String description) {
        return companyService.registerComapny(new Company(login, password, coampanyName, city, comopanyAddress, postalCode, nip, email, description, true));

    }

    @DeleteMapping("/deleteCompany")
    public Boolean deleteComapnyByID(@RequestParam UUID comapnyId) {
        return companyService.deleteComapny(comapnyId);
    }

    @PutMapping("/updateComapnyStatus")
    public Boolean updateStatus(@RequestParam UUID coampnyId, @RequestParam Boolean status) {
        return companyService.updateStatus(coampnyId, status);
    }

    @PutMapping("/updateComapnyPassword")
    public Boolean updatePassword(@RequestParam("company_id") UUID companyId, @RequestParam("password") String password, @RequestParam("confirmpassword") String confirmPassword) {
        return companyService.updatePassword(companyId, password, confirmPassword);
    }

    @PutMapping("/updateCompanyLogin")
    public Boolean updateLogin(@RequestParam("company_id") UUID companyId, @RequestParam("login") String login) {
        return companyService.updateLogin(companyId, login);
    }

    @PutMapping("/upadateComapnyRole")
    public Boolean updateCompanyRole(@RequestParam("company_id") UUID companyId, @RequestParam("role") String role) {
        return companyService.updateRole(companyId, role);
    }

    @PutMapping("/upadateCoampny")
    public Boolean updateCompany(@RequestParam UUID comapnyId, String login, String password, String confirmPassword, String coampanyName, String city, String comopanyAddress, Integer postalCode, Integer nip, String email, String description) {
        return companyService.updateComapny(comapnyId, login, password, confirmPassword, coampanyName, city, comopanyAddress, postalCode, nip, email, description);
    }


}
