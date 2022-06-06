package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.CompanyRequest;
import com.fedag.internship.domain.dto.CompanyRequestUpdate;
import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable Long id) {
        CompanyResponse companyResponse = Optional.of(id)
                .map(companyService::getCompanyById)
                .map(companyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, OK);
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(@PageableDefault(size = 5) Pageable pageable) {
        Page<CompanyResponse> companies = companyService.getAllCompanies(pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(companies, OK);
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestParam Long userId,
                                           @RequestBody @Valid CompanyRequest companyRequest) {
        CompanyResponse companyResponse = Optional.ofNullable(companyRequest)
                .map(companyMapper::fromRequest)
                .map(companyEntity -> companyService.createCompany(userId, companyEntity))
                .map(companyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id,
                                           @RequestBody CompanyRequestUpdate companyRequestUpdate) {
        CompanyResponse companyResponse = Optional.ofNullable(companyRequestUpdate)
                .map(companyMapper::fromRequestUpdate)
                .map(company -> companyService.updateCompany(id, company))
                .map(companyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(OK);
    }
}
