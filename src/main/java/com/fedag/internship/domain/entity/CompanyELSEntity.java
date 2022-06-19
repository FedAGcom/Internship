package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "companies")
public class CompanyELSEntity {
    @Id
    private String id;
    @Field
    private String name;

    private Long company_id;

    public CompanyELSEntity(String name) {
        this.name = name;
    }
}
