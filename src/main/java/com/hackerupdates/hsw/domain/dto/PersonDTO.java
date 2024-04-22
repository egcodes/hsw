package com.hackerupdates.hsw.domain.dto;

import com.hackerupdates.hsw.enums.Status;
import java.time.Instant;
import lombok.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Builder
@Data
public class PersonDTO {

    private Long id;

    @NotEmpty
    @Size(min=3, max=50)
    private String userName;

    @NotEmpty
    @Size(min=2, max=25)
    private String name;

    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String mail;

    private Status status;

    private Instant createDate;

    private String about;

    private boolean darkTheme;

    private String code;
}
