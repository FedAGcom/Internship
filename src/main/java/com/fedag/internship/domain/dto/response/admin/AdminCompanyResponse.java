package com.fedag.internship.domain.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Компания для админа", description = "Информация о компании")
public class AdminCompanyResponse {
    @Schema(description = "Идентификатор компании",
            example = "1")
    private Long id;

    @Schema(description = "Имя компании",
            maxLength = 255,
            minLength = 1,
            example = "some name")
    private String name;

    @Schema(description = "Описание компании",
            maxLength = 500,
            minLength = 1,
            example = "some description")
    private String description;

    @Schema(description = "Рейтинг компании",
            maximum = "10",
            minimum = "1",
            example = "5")
    private Double rating;

    @Schema(description = "Место нахождения компании компании",
            maxLength = 255,
            minLength = 1,
            example = "some location")
    private String location;

    @Schema(description = "Ссылка на компанию",
            maxLength = 255,
            minLength = 1,
            example = "someUrl.com")
    private String link;

    @Schema(description = "Статус сущности компании",
            example = "true")
    private Boolean active;

    @Schema(description = "Идентификатор пользователя компании", example = "1")
    private Long userId;

    @Schema(description = "Список идентификаторов пользователей у которых компания в избранном",
            example = "[1, 2, 3]")
    private List<Long> favouriteCompanyForUserIds;

    @Schema(description = "Список идентификаторов комментариев компании", example = "[1, 2, 3]")
    private List<Long> commentIds;
}
