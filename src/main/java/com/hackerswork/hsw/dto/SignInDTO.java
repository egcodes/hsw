package com.hackerswork.hsw.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignInDTO {

    @NotEmpty
    @Size(max=50)
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$")
    private String userName;

    @NotEmpty
    @Size(min=6, max=50)
    private String password;

}
