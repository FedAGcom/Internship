package com.fedag.internship.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompilerRequest {
    private String clientId;
    private String clientSecret;
    private String script;
    private String language;
    private String versionIndex;
}
