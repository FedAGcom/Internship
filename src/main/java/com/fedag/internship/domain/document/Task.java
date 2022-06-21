package com.fedag.internship.domain.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

/**
 * class Task is domain-document Task object.
 *
 * @author damir.iusupov
 * @since 2022-06-13
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "tasks")
public class Task {
    @Id
    private String id;

    @Field(type = Text)
    private String someField;
}
