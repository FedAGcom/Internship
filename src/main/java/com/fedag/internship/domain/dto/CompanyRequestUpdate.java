package com.fedag.internship.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyRequestUpdate {
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;
}
