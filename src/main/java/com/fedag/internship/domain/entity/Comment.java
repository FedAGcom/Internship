package com.fedag.internship.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * class Comment is domain-entity comment object.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends AuditableEntity {
    /**
     * id of all Entities
     */
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_comments_id")
    @GenericGenerator(name = "seq_comments_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_comments_id")})
    private Long id;
    /**
     * text of Comment
     */
    private String text;
}
