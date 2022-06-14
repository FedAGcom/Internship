package com.fedag.internship.register;

import com.fedag.internship.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "confirmation_tokens")
@Entity
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
        this.expiredAt = expiredAt;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
