package com.hackerswork.hsw.dto;

import com.hackerswork.hsw.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Builder
@Data
public class PersonDTO {

    @ApiModelProperty(hidden=true)
    private Long id;

    @NotEmpty
    @Size(min=2, max=50)
    @Pattern(regexp = "^[a-zA-Z0-9çşğüöıÇŞĞÜÖİ]+")
    @ApiModelProperty(position = 1, required = true)
    private String name;

    @NotEmpty
    @Size(min=3, max=50)
    @ApiModelProperty(position = 2, required = true)
    private String userName;

    @NotEmpty
    @Size(min=5, max=50)
    @Pattern(regexp = "^(.+)@(\\S+)$")
    @ApiModelProperty(position = 3, required = true)
    private String mail;

    @ApiModelProperty(hidden=true)
    private Status status;

    @ApiModelProperty(hidden=true)
    private OffsetDateTime createDate;

}
