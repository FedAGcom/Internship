package com.fedag.internship.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Создание компании", description = "Запрос для создания компании")
public class CompanyRequest {
    @Schema(description = "Имя компании",
            maxLength = 255,
            minLength = 1,
            example = "some name")
    @NotBlank
    @Size(max = 255)
    private String name;

    @Schema(description = "Описание компании",
            maxLength = 500,
            minLength = 1,
            example = "some description")
    @NotBlank
    @Size(max = 500)
    private String description;

    @Schema(description = "Рейтинг компании",
            maximum = "10",
            minimum = "1",
            example = "5")
    @Min(1)
    @Max(10)
    private Double rating;

    @Schema(description = "Место нахождения компании компании",
            maxLength = 255,
            minLength = 1,
            example = "some location")
    @NotBlank
    @Size(max = 255)
    private String location;

    @Schema(description = "Ссылка на компанию",
            maxLength = 255,
            minLength = 1,
            example = "someUrl.com")
    @NotBlank
    @Size(max = 255)
    private String link;
}
