package com.fedag.internship.domain.document;

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
public class CompanyElasticSearchEntity {
    @Id
    private String id;
    @Field
    private String name;

    private Long companyEntityId;

    public CompanyElasticSearchEntity(String name, Long companyEntityId) {
        this.name = name;
        this.companyEntityId = companyEntityId;
    }
}
