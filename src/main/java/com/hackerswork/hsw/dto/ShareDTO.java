package com.hackerswork.hsw.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShareDTO {

    @ApiModelProperty(hidden=true)
    private Long id;

    @ApiModelProperty(hidden=true)
    private Long personId;

    @ApiModelProperty(hidden=true)
    private OffsetDateTime createdTime;

    @NotEmpty
    @Size(min = 10, max = 280)
    private String text;

}
