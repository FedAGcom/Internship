package com.fedag.internship.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fedag.internship.domain.entity.Role;
import com.fedag.internship.validation.annotation.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Создание пользователя", description = "Запрос для создания пользователя")
public class UserRequest {
    @Schema(description = "Почтовый адрес пользователя",
            maxLength = 255,
            minLength = 1,
            example = "some@mail.com")
    @Email
    @Size(max = 255)
    private String email;

    @Schema(description = "Имя пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someName")
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Schema(description = "Фамилия пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someSurname")
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @Schema(description = "Пароль пользователя",
            maxLength = 255,
            minLength = 1,
            example = "qwerty")
    @NotBlank
    @Size(max = 255)
    private String password;

    @Schema(description = "Статус пользователя",
            example = "true")
    @NotNull
    private Boolean enabled;

    @Schema(description = "Роль пользователя",
            example = "USER")
    @NotNull
    private Role role;
}
