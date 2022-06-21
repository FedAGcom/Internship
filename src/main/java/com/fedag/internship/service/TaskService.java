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
    Task getTaskById(String id);

    Page<Task> getAllTasks(Pageable pageable);

    Task createTask(Task task);

    Task updateTask(String id, Task task);

    void deleteTask(String id);
}
