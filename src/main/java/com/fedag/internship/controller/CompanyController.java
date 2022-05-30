package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id) {
        CompanyDto companyDto = companyService.getCompanyById(id);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyDto companyDto) {
        companyService.createCompany(companyDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCompany(@RequestBody CompanyDto companyDto) {
        companyService.updateCompany(companyDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
