package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.document.Task;
import com.fedag.internship.domain.dto.request.TaskRequest;
import com.fedag.internship.domain.dto.request.TaskRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminTaskResponse;
import com.fedag.internship.domain.dto.response.user.TaskResponse;

public interface TaskMapper {
    TaskResponse toResponse(Task task);

    AdminTaskResponse toAdminResponse(Task task);

    Task fromRequest(TaskRequest taskRequest);

    Task fromRequestUpdate(TaskRequestUpdate taskRequestUpdate);

    Task merge(Task source, Task target);
}
