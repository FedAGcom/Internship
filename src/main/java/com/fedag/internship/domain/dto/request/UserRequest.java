package com.fedag.internship.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fedag.internship.domain.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    @Size(max = 255)
    private String email;

    @Schema(description = "Имя пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someName")
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Schema(description = "Пароль пользователя",
            maxLength = 255,
            minLength = 1,
            example = "qwerty")
    @NotBlank
    @Size(max = 255)
    private String password;

    @Schema(description = "Роль пользователя",
            maxLength = 255,
            minLength = 1,
            example = "USER")
    @NotBlank
    @Size(max = 255)
    private Role role;

    @Schema(description = "Фамилия пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someSurname")
    @NotBlank
    @Size(max = 255)
    private String lastName;
}
