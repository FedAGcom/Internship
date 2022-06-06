package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_positions")
public class TraineePositionEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_users_id")
    @GenericGenerator(name = "seq_users_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_users_id")})
    private Long id;

    @CreatedDate
    private LocalDateTime date;

    private String name;
    private String employeePosition;
    private String status;
    private String location;
    private String documents;
    private String url;
    private String text;

}
