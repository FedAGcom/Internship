package com.fedag.internship.domain.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Пользователь для админа", description = "Информация о пользователе")
public class AdminUserResponse {
    @Schema(description = "Идентификатор пользователя",
            example = "1")
    private Long id;

    @Schema(description = "Почтовый адрес пользователя",
            maxLength = 255,
            minLength = 1,
            example = "some@mail.com")
    private String email;

    @Schema(description = "Имя пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someName")
    private String firstName;

    @Schema(description = "Фамилия пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someSurname")
    private String lastName;

    @Schema(description = "Роль пользователя",
            maxLength = 255,
            minLength = 1,
            example = "USER")
    private String role;

    @Schema(description = "Статус пользователя",
            example = "false")
    private Boolean enabled;

    @Schema(description = "Дата создания пользователя")
    private LocalDateTime created;

    @Schema(description = "Идентификатор компании", example = "1")
    private Long companyId;

    @Schema(description = "Список идентификаторов комментариев пользователя", example = "[1, 2, 3]")
    private List<Long> commentIds;

    @Schema(description = "Список идентификаторов избранных компаний", example = "[1, 2, 3]")
    private List<Long> favouriteCompanyIds;

    @Schema(description = "Список идентификаторов избранных стажировок", example = "[1, 2, 3]")
    private List<Long> favouriteTraineePositionIds;
}
