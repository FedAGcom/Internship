package com.fedag.internship.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

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
     * text of Comment
     */
    private String text;
}
