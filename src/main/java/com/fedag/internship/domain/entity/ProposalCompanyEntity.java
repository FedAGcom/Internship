package com.fedag.internship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * class ProposalCompany is domain-entity proposal company object.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "proposal_company")
public class ProposalCompanyEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_proposal_company_id")
    @GenericGenerator(name = "seq_proposal_company_id", strategy = "sequence",
            parameters = {@Parameter(name = "sequence", value = "seq_proposal_company_id")})
    private Long id;

    private String name;

    @Enumerated(STRING)
    private Status status;
}
