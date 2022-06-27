package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Accessors(chain = true)
@Table(name = "trainee_positions")
@EntityListeners(AuditingEntityListener.class)
public class TraineePositionEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_trainee_positions_id")
    @GenericGenerator(name = "seq_trainee_positions_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_trainee_positions_id")})
    private Long id;

    private LocalDateTime date;

    @Field
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity company;

    private String name;
    private String employeePosition;
    private String status;
    private String location;
    private String documents;
    private String url;
    private String text;
    private Boolean active;

    @Setter(PRIVATE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinTable(name = "favourite_trainee_positions",
            joinColumns = @JoinColumn(name = "trainee_position_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> users = new ArrayList<>();

    @Setter(PRIVATE)
    @OneToMany(mappedBy = "traineePosition", fetch = LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    public void addFavouriteTraineePositionToUser(UserEntity userEntity) {
        this.users.add(userEntity);
        userEntity.getFavouriteTraineePositions().add(this);
    }

    public void removeFavouriteTraineePositionFromUser(UserEntity userEntity) {
        this.users.remove(userEntity);
        userEntity.getFavouriteTraineePositions().remove(this);
    }

    public void addComments(CommentEntity commentEntity) {
        this.comments.add(commentEntity);
        commentEntity.setTraineePosition(this);
    }

    public void removeComments(CommentEntity commentEntity) {
        this.comments.remove(commentEntity);
    }
}
