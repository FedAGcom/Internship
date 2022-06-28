package com.fedag.internship.service.impl;

import com.fedag.internship.domain.document.Task;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.TaskMapper;
import com.fedag.internship.repository.TaskElasticsearchRepository;
import com.fedag.internship.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class TaskMongoServiceImpl
 *
 * @author damir.iusupov
 * @since 2022-06-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final TaskElasticsearchRepository elasticsearchRepository;
    private final TaskMapper taskMapper;

    @Override
    public Task findById(String id) {
        log.info("Получение задания c Id: {}", id);
        Task result = elasticsearchRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Задание с Id: {} не найдено", id);
                    throw new EntityNotFoundException("Task", "Id", id);
                });
        log.info("Задание c Id: {} получено", id);
        return result;
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        log.info("Получение страницы с заданиями");
        Page<Task> result = elasticsearchRepository.findAll(pageable);
        log.info("Страница с заданиями получена");
        return result;
    }

    @Override
    @Transactional
    public Task create(Task task) {
        log.info("Создание задания");
        Task result = elasticsearchRepository.save(task);
        log.info("Задание создано");
        return result;
    }

    @Override
    @Transactional
    public Task update(String id, Task task) {
        log.info("Обновление задания с Id: {}", id);
        Task target = this.findById(id);
        Task update = taskMapper.merge(task, target);
        Task result = elasticsearchRepository.save(update);
        log.info("Задание с Id: {} обновлено", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        log.info("Удаление задания с Id: {}", id);
        this.findById(id);
        elasticsearchRepository.deleteById(id);
        log.info("Задание с Id: {} удалено", id);
    }
}
