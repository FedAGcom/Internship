package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * interface CommentRepository extends {@link JpaRepository} for class {@link CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT AVG(c.rating) FROM CommentEntity c GROUP BY c.company HAVING c.company.id = :companyId")
    Double getAvgRatingOfCompany(Long companyId);
}
