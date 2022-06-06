package com.fedag.internship.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * class CommentDto for view from Comment's Dto layer of Comment.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Комментарий", description = "Информация о комментарии")
public class CommentResponse {
    @Schema(description = "Идентификатор Комментария",
            example = "1")
    private Long id;

    @Schema(description = "Текст комментария",
            maxLength = 500,
            minLength = 1,
            example = "some text")
    private String text;

    @Schema(description = "Дата создания комментария")
    private LocalDateTime created;

    @Schema(description = "Дата обновления комментария")
    private LocalDateTime updated;

    @Schema(description = "Идентификатор пользователя компании", example = "1")
    private Long userId;
}
