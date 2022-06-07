package com.fedag.internship.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Обновление пользователя", description = "Запрос для обновления пользователя")
public class UserRequestUpdate {
    @Schema(description = "Почтовый адрес пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someUpd@mail.com")
    @Size(max = 255)
    private String email;

    @Schema(description = "Имя пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someName upd")
    @Size(max = 255)
    private String firstName;

    @Schema(description = "Фамилия пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someSurname upd")
    @Size(max = 255)
    private String lastName;
}
