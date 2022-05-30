package com.fedag.internship.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "company_profiles")
public class CompanyEntity extends BaseEntity{
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;
}
