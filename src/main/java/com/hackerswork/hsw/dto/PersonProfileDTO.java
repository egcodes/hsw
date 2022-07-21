package com.hackerswork.hsw.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonProfileDTO {

    private Long id;

    @NotEmpty
    @Size(min=2, max=50)
    private String name;

    @Size(max=280)
    private String about;

}
