package com.fedag.internship.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PRIVATE;

/**
 * abstract class BaseEntity - super-class for others Entities.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Getter
@Setter(PRIVATE)
@MappedSuperclass
public abstract class BaseEntity {
    /**
     * id of all Entities
     */
    @Id
    @GenericGenerator(name = "seq_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_id")})
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_id")
    private Long id;
}

