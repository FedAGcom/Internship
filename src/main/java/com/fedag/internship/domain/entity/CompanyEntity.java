package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "company_profiles")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_company_profiles_id")
    @GenericGenerator(name = "seq_company_profiles_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_company_profiles_id")})
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String location;
    private String link;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Setter(PRIVATE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinTable(name = "favourite_companies",
            joinColumns = @JoinColumn(name = "company_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> users = new ArrayList<>();

    @Setter(PRIVATE)
    @OneToMany(mappedBy = "company", fetch = LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    public void addFavouriteCompanyToUser(UserEntity userEntity) {
        this.users.add(userEntity);
        userEntity.getFavouriteCompanies().add(this);
    }

    public void removeFavouriteCompany(UserEntity userEntity) {
        this.users.remove(userEntity);
        userEntity.getFavouriteCompanies().remove(this);
    }

    public void addComments(CommentEntity commentEntity) {
        this.comments.add(commentEntity);
        commentEntity.setCompany(this);
    }

    public void removeComments(CommentEntity commentEntity) {
        this.comments.remove(commentEntity);
    }
}
