package com.fedag.internship.service;

import com.fedag.internship.domain.document.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * interface TaskElasticsearchService
 *
 * @author damir.iusupov
 * @since 2022-06-13
 */
public interface TaskService {
    Task findById(String id);

    Page<Task> findAll(Pageable pageable);

    Task create(Task task);

    Task update(String id, Task task);

    void deleteById(String id);
}
