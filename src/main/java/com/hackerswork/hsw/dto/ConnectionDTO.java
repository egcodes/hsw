package com.hackerswork.hsw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ConnectionDTO {

    private Long personId;
    private Long connectionId;
    private boolean hidden;
    private boolean blocked;
    private boolean pinned;

}
