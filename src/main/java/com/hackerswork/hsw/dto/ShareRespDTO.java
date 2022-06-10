package com.hackerswork.hsw.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class ShareRespDTO {

    private Long id;

    private String userName;

    private String name;

    @NotEmpty
    @Size(min = 10, max = 280)
    private String text;

    @NotEmpty
    @Setter
    private String createdTime;

}
