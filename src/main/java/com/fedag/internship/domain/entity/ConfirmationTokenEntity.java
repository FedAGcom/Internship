package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "confirmation_tokens")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ConfirmationTokenEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_confirmation_tokens_id")
    @GenericGenerator(name = "seq_confirmation_tokens_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_confirmation_tokens_id")})
    private Long id;
    private String token;

    public ConfirmationTokenEntity(String token, UserEntity userEntity, LocalDateTime expiredAt) {
        this.token = token;
        this.userEntity = userEntity;
        this.expired = expiredAt;
    }

    @CreatedDate
    private LocalDateTime created;

    private LocalDateTime expired;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
