package com.hackerswork.hsw.dto;

import java.time.Instant;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ShareDTO {

    private String userName;

    private String name;

    @NotEmpty
    @Size(min = 10, max = 280)
    private String text;

    @NotEmpty
    private Instant createdTime;

}
