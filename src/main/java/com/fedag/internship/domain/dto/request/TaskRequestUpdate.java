package com.fedag.internship.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Обновление задания", description = "Запрос для обновления задания")
public class TaskRequestUpdate {
    @Schema(description = "Поле задания",
            example = "someField upd")
    private String someField;
}
