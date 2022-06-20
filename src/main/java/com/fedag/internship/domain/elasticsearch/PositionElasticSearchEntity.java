package com.fedag.internship.domain.elasticsearch;

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
@Document(indexName = "positions")
public class PositionElasticSearchEntity {
    @Id
    private String id;
    private String name;
    @Field
    private Long companyEntityId;

    public PositionElasticSearchEntity(String name, Long companyEntityId) {
        this.name = name;
        this.companyEntityId = companyEntityId;
    }
}
