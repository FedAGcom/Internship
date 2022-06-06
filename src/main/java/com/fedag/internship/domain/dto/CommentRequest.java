package com.fedag.internship.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * class CommentCreateDto for create Dto layer of Comment.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Создание комментария", description = "Запрос для создания комментария")
public class CommentRequest {
    @Schema(description = "Текст комментария",
            maxLength = 500,
            minLength = 1,
            example = "some text")
    @NotBlank
    @Size(max = 500)
    private String text;
}
