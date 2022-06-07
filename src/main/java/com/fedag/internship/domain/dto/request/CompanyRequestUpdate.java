package com.fedag.internship.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Обновление компании", description = "Запрос для обновления компании")
public class CompanyRequestUpdate {
    @Schema(description = "Имя компании",
            maxLength = 255,
            minLength = 1,
            example = "some name upd")
    @Size(max = 255)
    private String name;

    @Schema(description = "Описание компании",
            maxLength = 500,
            minLength = 1,
            example = "some description upd")
    @Size(max = 500)
    private String description;

    @Schema(description = "Рейтинг компании",
            maximum = "10",
            minimum = "1",
            example = "4")
    @Min(1)
    @Max(10)
    private Double rating;

    @Schema(description = "Место нахождения компании компании",
            maxLength = 255,
            minLength = 1,
            example = "some location upd")
    @Size(max = 255)
    private String location;

    @Schema(description = "Ссылка на компанию",
            maxLength = 255,
            minLength = 1,
            example = "someUrlUpd.com")
    @Size(max = 255)
    private String link;
}
