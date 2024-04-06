package com.hackerupdates.hsw.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonProfileDTO {

    private Long id;

    @NotEmpty
    @Size(min=2, max=25)
    private String name;

    @Size(max=280)
    private String about;

    @Size(max=50)
    private String mail;

}