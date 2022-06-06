package com.fedag.internship.domain.dto;

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
public class CommentResponse {
    private Long id;
    private String text;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long userId;
}
