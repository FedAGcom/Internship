package com.fedag.internship.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Обновление позиции для стажировки", description = "Запрос для обновления позиции")
public class TraineePositionRequestUpdate {
    @Schema(description = "Название позиции",
            maxLength = 255,
            minLength = 1,
            example = "some name upd")
    @Size(max = 255)
    private String name;

    @Schema(description = "Должность для стажировки",
            maxLength = 255,
            minLength = 1,
            example = "someEmployeePosition upd")
    @Size(max = 255)
    private String employeePosition;

    @Schema(description = "Статус позиции",
            maxLength = 255,
            minLength = 1,
            example = "some status upd")
    @Size(max = 255)
    private String status;

    @Schema(description = "Адрес позиции для стажировки",
            maxLength = 255,
            minLength = 1,
            example = "some location upd")
    @Size(max = 255)
    private String location;

    @Schema(description = "Необходимые документы для стажировки",
            maxLength = 255,
            minLength = 1,
            example = "some documents upd")
    @Size(max = 255)
    private String documents;

    @Schema(description = "Ссылка на сайт организатора стажировки",
            maxLength = 255,
            minLength = 1,
            example = "some documents upd")
    @Size(max = 255)
    private String url;

    @Schema(description = "Описание позиции",
            maxLength = 500,
            minLength = 1,
            example = "some text upd")
    @Size(max = 500)
    private String text;
}
