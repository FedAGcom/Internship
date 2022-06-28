package com.fedag.internship.domain.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Позиция стажировки для админа", description = "Информация о позиции")
public class AdminTraineePositionResponse {
    @Schema(description = "Идентификатор позиции",
            example = "1")
    private Long id;

    @Schema(description = "Идентификатор компании",
            example = "1")
    private Long companyId;

    @Schema(description = "Дата создания позиции")
    private LocalDateTime date;

    @Schema(description = "Название позиции",
            maxLength = 255,
            minLength = 1,
            example = "some name")
    private String name;

    @Schema(description = "Должность для стажировки",
            maxLength = 255,
            minLength = 1,
            example = "someEmployeePosition")
    private String employeePosition;

    @Schema(description = "Статус позиции",
            maxLength = 255,
            minLength = 1,
            example = "some status")
    private String status;

    @Schema(description = "Адрес позиции для стажировки",
            maxLength = 255,
            minLength = 1,
            example = "some location")
    private String location;

    @Schema(description = "Необходимые документы для стажировки",
            maxLength = 255,
            minLength = 1,
            example = "some documents")
    private String documents;

    @Schema(description = "Ссылка на сайт организатора стажировки",
            maxLength = 255,
            minLength = 1,
            example = "some documents")
    private String url;

    @Schema(description = "Описание позиции",
            maxLength = 500,
            minLength = 1,
            example = "some text")
    private String text;

    @Schema(description = "Статус сущности компании",
            example = "true")
    private Boolean active;

    @Schema(description = "Список идентификаторов пользователей у которых позиция в избранном",
            example = "[1, 2, 3]")
    private List<Long> favouritePositionForUserIds;

    @Schema(description = "Список идентификаторов комментариев позиции", example = "[1, 2, 3]")
    private List<Long> commentIds;
}
