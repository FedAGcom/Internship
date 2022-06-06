package com.fedag.internship.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CompanyResponse {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;
    private Long userId;
}
