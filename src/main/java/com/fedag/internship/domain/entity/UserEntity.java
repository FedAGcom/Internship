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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_users_id")
    @GenericGenerator(name = "seq_users_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_users_id")})
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String timeOfCreating;
}
