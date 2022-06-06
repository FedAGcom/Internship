package com.fedag.internship.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * class CommentUpdateDto for update Dto layer of Comment.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Обновление комментария", description = "Запрос для обновления комментария")
public class CommentRequestUpdate {
    @Schema(description = "Текст комментария",
            maxLength = 500,
            minLength = 1,
            example = "some text upd")
    @NotBlank
    @Size(max = 500)
    private String text;
}
