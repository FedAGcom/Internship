package com.fedag.internship.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * class ProposalCompanyResponse for create Dto layer of {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Создание предложения о создании компании",
        description = "Информация о создании предложения")
public class ProposalCompanyRequest {
    @Schema(description = "Имя компании",
            maxLength = 255,
            minLength = 1,
            example = "some name")
    @NotBlank
    @Size(max = 255)
    private String name;

    @Schema(description = "Описание компании",
            maxLength = 500,
            minLength = 1,
            example = "some description")
    @NotBlank
    @Size(max = 255)
    private String description;
}