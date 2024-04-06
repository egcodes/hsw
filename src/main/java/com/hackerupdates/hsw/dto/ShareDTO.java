package com.hackerupdates.hsw.dto;

import java.time.Instant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class ShareDTO {

    private Long id;

    @Setter
    private String userName;

    @Setter
    private String name;

    @NotEmpty
    @Size(min = 10, max = 280)
    private String text;

    private Instant createdTime;

}
