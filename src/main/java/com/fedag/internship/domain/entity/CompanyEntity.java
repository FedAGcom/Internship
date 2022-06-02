package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_profiles")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_company_profiles_id")
    @GenericGenerator(name = "seq_company_profiles_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_company_profiles_id")})
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;
}
