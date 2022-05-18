package com.hackerswork.hsw.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActivityDTO {

    @ApiModelProperty(hidden=true)
    private Long id;

    @NotEmpty
    @ApiModelProperty(position = 1, required = true)
    private Long personId;

    @NotEmpty
    @ApiModelProperty(position = 2, required = true)
    private Long lastActivityTime;

}
