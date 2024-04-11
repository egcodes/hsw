package com.hackerupdates.hsw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ActivityDTO {

    private Long personId;
    private String userName;
    private String name;
    private Long lastActivityTime;

}
