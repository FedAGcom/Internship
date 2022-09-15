package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "resumes")
public class ResumeEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_resumes_id")
    @GenericGenerator(name = "seq_resumes_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_resumes_id")})
    private Long id;
    private String phone;
    private String location;
    private byte[] resumeFile;
    private String resumeFileType;
    private String coverLetter;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
