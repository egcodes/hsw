package com.hackerupdates.hsw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ConnectionDTO {

    public ConnectionDTO(Long connectionId, boolean pinned) {
        this.connectionId = connectionId;
        this.pinned = pinned;
    }

    private Long personId;
    private Long connectionId;
    private boolean hidden;
    private boolean blocked;
    private boolean pinned;

}
