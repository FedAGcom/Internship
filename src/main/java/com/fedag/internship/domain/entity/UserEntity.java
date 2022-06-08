package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_users_id")
    @GenericGenerator(name = "seq_users_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_users_id")})
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    @CreatedDate
    private LocalDateTime created;

    @OneToOne(mappedBy = "user")
    private CompanyEntity company;

    @Setter(PRIVATE)
    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    @Setter(PRIVATE)
    @ManyToMany(mappedBy = "users", fetch = LAZY)
    private List<CompanyEntity> favouriteCompanies = new ArrayList<>();

    @Setter(PRIVATE)
    @ManyToMany(mappedBy = "users", fetch = LAZY)
    private List<TraineePositionEntity> favouriteTraineePositions = new ArrayList<>();

    public void addComments(CommentEntity commentEntity) {
        this.comments.add(commentEntity);
        commentEntity.setUser(this);
    }

    public void removeComments(CommentEntity commentEntity) {
        this.comments.remove(commentEntity);
    }

}
