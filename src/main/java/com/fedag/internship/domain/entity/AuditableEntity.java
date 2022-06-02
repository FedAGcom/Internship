package com.fedag.internship.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

/**
 * abstract class AuditableEntity - super-class for others Entities.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Getter
@Setter(PRIVATE)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {
    /**
     * date of object create
     */
    @CreatedDate
    private LocalDateTime created;

    /**
     * date of object update
     */
    @LastModifiedDate
    private LocalDateTime updated;
}
