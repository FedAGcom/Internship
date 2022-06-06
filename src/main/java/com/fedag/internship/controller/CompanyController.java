package com.fedag.internship.controller;

import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.domain.dto.CompanyRequest;
import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable Long id) {
        CompanyDto companyDto = companyService.getCompanyById(id);
        CompanyResponse companyResponse = companyMapper.toResponse(companyDto);
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(@PageableDefault(size = 5) Pageable pageable) {
        Page<CompanyResponse> companies = companyService.getAllCompanies(pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyRequest companyRequest) {
        CompanyDto companyDto = companyMapper.toDto(companyRequest);
        companyService.createCompany(companyDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCompany(@RequestBody CompanyRequest companyRequest) {
        CompanyDto companyDto = companyMapper.toDto(companyRequest);
        companyService.updateCompany(companyDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyResponse>> search(@RequestParam String keyword, Pageable pageable) {
        Page<CompanyResponse> companies = companyService.searchCompanyByName(keyword, pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
}
