package com.fedag.internship.domain.dto.request;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * class ProposalCompanyResponse for update Dto layer of {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Обновление предложения о создании компании",
        description = "Информация об обновлении предложения")
public class ProposalCompanyRequestUpdate {
    @Schema(description = "Имя компании",
            maxLength = 255,
            minLength = 1,
            example = "some name upd")
    @Size(max = 255)
    private String name;

    @Schema(description = "Описание компании",
            maxLength = 500,
            minLength = 1,
            example = "some description upd")
    @Size(max = 500)
    private String description;
}
