package com.hackerupdates.hsw.dto;

import com.hackerupdates.hsw.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import lombok.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Builder
@Data
public class PersonDTO {

    @ApiModelProperty(hidden=true)
    private Long id;

    @NotEmpty
    @Size(min=3, max=50)
    @ApiModelProperty(position = 1, required = true)
    private String userName;

    @NotEmpty
    @Size(min=2, max=25)
    private String name;

    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String mail;

    @ApiModelProperty(hidden=true)
    private Status status;

    @ApiModelProperty(hidden=true)
    private Instant createDate;

    @ApiModelProperty(hidden=true)
    private String about;

    @ApiModelProperty(hidden=true)
    private boolean darkTheme;

    @ApiModelProperty(hidden=true)
    private String code;
}
