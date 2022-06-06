package com.fedag.internship.domain.dto;

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
public class CommentRequestUpdate {
    @NotBlank
    @Size(max = 500)
    private String text;
}
