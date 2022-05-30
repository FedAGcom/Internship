package com.fedag.internship.domain.dto;

import lombok.Data;


@Data
public final class CompanyDto {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;
}
