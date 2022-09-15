package com.fedag.internship.domain.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Резюме для админа", description = "Информация о резюме")
public class AdminResumeResponse {
    @Schema(description = "Идентификатор резюме",
            example = "1")
    private Long id;

    @Schema(description = "Номер телефона пользователя",
            maxLength = 255,
            minLength = 1,
            example = "88005553535")
    private String phone;

    @Schema(description = "Место проживания пользователя",
            maxLength = 255,
            minLength = 1,
            example = "г. Санкт-Петербург")
    private String location;

    @Schema(description = "Файл",
            maxLength = 255,
            minLength = 1)
    private String resumeFile;

    @Schema(description = "Тип Файла",
            maxLength = 64,
            minLength = 1)
    private String resumeFileType;

    @Schema(description = "Сопроводительное письмо",
            maxLength = 255,
            minLength = 1)
    private String coverLetter;

    @Schema(description = "Идентификатор юзера",
            example = "1")
    private Long userId;
}
