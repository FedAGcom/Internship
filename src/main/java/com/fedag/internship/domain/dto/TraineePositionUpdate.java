package com.fedag.internship.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TraineePositionUpdate {
    private String name;
    private String employeePosition;
    private String status;
    private String location;
    private String documents;
    private String url;
    private String text;
}
