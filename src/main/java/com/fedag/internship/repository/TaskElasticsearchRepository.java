package com.fedag.internship.repository;

import com.fedag.internship.domain.document.Task;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * interface TaskElasticRepository extends {@link ElasticsearchRepository} for class {@link Task}.
 *
 * @author damir.iusupov
 * @since 2022-06-13
 */
@Repository
public interface TaskElasticsearchRepository extends ElasticsearchRepository<Task, String> {
}
