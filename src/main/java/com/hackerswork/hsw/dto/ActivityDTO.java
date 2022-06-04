package com.hackerswork.hsw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ActivityDTO {

    private String userName;
    private String name;
    private Long lastActivityTime;

}
