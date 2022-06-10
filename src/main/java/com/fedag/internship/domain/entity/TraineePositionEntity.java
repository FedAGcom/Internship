package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.bridge.builtin.IntegerBridge;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
@Indexed
public class TraineePositionEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_trainee_positions_id")
    @GenericGenerator(name = "seq_trainee_positions_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_trainee_positions_id")})
    private Long id;

    @CreatedDate
    private LocalDateTime date;

    @Field
    @FieldBridge(impl = IntegerBridge.class)
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

    public void removeFavouriteTraineePosition(UserEntity userEntity) {
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
