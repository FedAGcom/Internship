package com.fedag.internship.domain.dto.request;

import com.fedag.internship.validation.annotation.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Вход пользователя в приложении", description = "Аутентификация пользователя")
public class AuthenticationRequest {
    @Schema(description = "Почтовый адрес пользователя",
            maxLength = 255,
            minLength = 1,
            example = "some@mail.com")
    @Email
    @Size(max = 255)
    private String email;

    @Schema(description = "Пароль пользователя",
            maxLength = 255,
            minLength = 1,
            example = "qwerty")
    @NotBlank
    @Size(max = 255)
    private String password;
}
