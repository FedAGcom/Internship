package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "company_profiles")
@Indexed
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_company_profiles_id")
    @GenericGenerator(name = "seq_company_profiles_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_company_profiles_id")})
    private Long id;
    @Field
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
