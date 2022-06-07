package com.fedag.internship.domain.dto.response;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.domain.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * class ProposalCompanyResponse for view from ProposalCompany's Dto layer of {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Предложение о создании компании", description = "Информация о предложении")
public class ProposalCompanyResponse {
    @Schema(description = "Идентификатор предложения",
            example = "1")
    private Long id;

    @Schema(description = "Имя компании",
            maxLength = 255,
            minLength = 1,
            example = "some name")
    private String name;

    @Schema(description = "Имя компании",
            maxLength = 100,
            minLength = 1,
            example = "NEW")
    private Status status;
}
