package com.fedag.internship.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Задание", description = "Информация о задании")
public class TaskResponse {
    @Schema(description = "Идентификатор задания",
            example = "some id")
    private String id;

    @Schema(description = "Поле задания",
            example = "someField")
    private String someField;
}
