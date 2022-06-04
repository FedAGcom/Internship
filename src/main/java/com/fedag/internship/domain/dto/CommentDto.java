package com.fedag.internship.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime created;
    private LocalDateTime updated;
}
