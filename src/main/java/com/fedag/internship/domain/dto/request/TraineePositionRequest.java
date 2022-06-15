package com.fedag.internship.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Создание позиции для стажировки", description = "Запрос для создания позиции")
public class TraineePositionRequest {
    @Schema(description = "Название позиции",
            maxLength = 255,
            minLength = 1,
            example = "some name")
    @NotBlank
    @Size(max = 255)
    private String name;

    @Schema(description = "Дата создания позиции")
    @NotNull
    private LocalDateTime date;

    @Schema(description = "Должность для стажировки",
            maxLength = 255,
            minLength = 1,
            example = "someEmployeePosition")
    @NotBlank
    @Size(max = 255)
    private String employeePosition;
}
