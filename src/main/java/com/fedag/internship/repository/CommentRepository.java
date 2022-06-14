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
    @Query(value =
            "SELECT AVG(rating) FROM comments INNER JOIN company_profiles ON company_id = id WHERE company_id = :companyId HAVING COUNT(rating) > 10"
                    , nativeQuery = true)
    Double getAvgRatingOfCompany(@Param("companyId") Long id);
}
