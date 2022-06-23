package com.fedag.internship.domain.dto.response.user;

import com.fedag.internship.domain.entity.CommentEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * class CommentDto for view from Comment's Dto layer of {@link CommentEntity}.
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

    @Schema(description = "Рейтинг у комментария",
            maximum = "10",
            minimum = "1",
            example = "2")
    @Min(1)
    @Max(10)
    private Double rating;

    @Schema(description = "Дата создания комментария")
    private LocalDateTime created;

    @Schema(description = "Дата обновления комментария")
    private LocalDateTime updated;

    @Schema(description = "Идентификатор пользователя оставившего комментарий", example = "1")
    private Long userId;

    @Schema(description = "Идентификатор позиции стажировки", example = "1")
    private Long traineePositionId;

    @Schema(description = "Идентификатор компании к которой оставлен комментарий", example = "1")
    private Long companyId;
}
