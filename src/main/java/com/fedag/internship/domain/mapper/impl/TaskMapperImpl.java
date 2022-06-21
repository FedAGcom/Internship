package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.document.Task;
import com.fedag.internship.domain.dto.request.TaskRequest;
import com.fedag.internship.domain.dto.request.TaskRequestUpdate;
import com.fedag.internship.domain.dto.response.TaskResponse;
import com.fedag.internship.domain.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
* class TaskMapperImpl
*
* @author damir.iusupov
* @since 2022-06-14
*/
@Component
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {
    private final ObjectMapper objectMapper;

    @Override
    public TaskResponse toResponse(Task task) {
        return new TaskResponse()
                .setId(task.getId())
                .setSomeField(task.getSomeField());
    }

    @Override
    public Task fromRequest(TaskRequest source) {
        return objectMapper.convertValue(source, Task.class);
    }

    @Override
    public Task fromRequestUpdate(TaskRequestUpdate source) {
        return objectMapper.convertValue(source, Task.class);
    }

    @Override
    public Task merge(Task source, Task target) {
        if (source.getSomeField() != null) {
            target.setSomeField(source.getSomeField());
        }
        return target;
    }
}
