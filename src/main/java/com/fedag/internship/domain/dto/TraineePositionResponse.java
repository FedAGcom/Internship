package com.fedag.internship.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TraineePositionResponse {
    private Long id;
    private LocalDateTime date;
    private String name;
    private String employeePosition;
    private String status;
    private String location;
    private String documents;
    private String url;
    private String text;
}
