package com.fedag.internship.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * class DtoErrorInfo for create Dto layer of Exceptions.
 *
 * @author damir.iusupov
 * @since 2022-06-06
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Ошибка или Исключение", description = "Ошибки или исключения возникающие в работе приложения")
public class DtoErrorInfo {
    @Schema(description = "Дата и время генерации ошибки")
    private LocalDateTime timestamp;

    @Schema(description = "Статус ошибки", example = "404")
    private Integer status;

    @Schema(description = "Имя ошибки", example = "Not Found")
    private String error;

    @Schema(description = "Класс ошибки", example = "com.fedag.internship.domain.exception.EntityNotFoundException")
    private String exception;

    @Schema(description = "Сообщение ошибки", example = "User with Id: 1 not found")
    private String message;
}
