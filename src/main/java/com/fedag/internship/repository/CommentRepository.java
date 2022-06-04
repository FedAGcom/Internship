package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface CommentRepository extends {@link JpaRepository} for class {@link CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
