package com.fedag.internship.exception.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
