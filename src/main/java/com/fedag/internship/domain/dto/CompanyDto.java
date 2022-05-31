package com.fedag.internship.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class CompanyDto {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;
}
