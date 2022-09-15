package com.fedag.internship.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fedag.internship.validation.annotation.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Обновление резюме", description = "Обновление резюме пользователя")
public class ResumeRequestUpdate {
    @Schema(description = "Номер телефона пользователя",
            maxLength = 255,
            minLength = 1,
            example = "88005553535")
    @Phone
    private String phone;

    @Schema(description = "Место проживания пользователя",
            maxLength = 255,
            minLength = 1,
            example = "г. Санкт-Петербург")
    private String location;

    @Schema(description = "Файл")
    private byte[] resumeFile;

    @Schema(description = "Тип Файла",
            maxLength = 64,
            minLength = 1)
    private String resumeFileType;

    @Schema(description = "Сопроводительное письмо",
            maxLength = 255,
            minLength = 1)
    private String coverLetter;
}
