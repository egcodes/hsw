package com.hackerswork.hsw.dto;

import com.hackerswork.hsw.enums.Status;
import lombok.*;

import java.time.OffsetDateTime;

@Builder
@Data
public class StatusHistoryDTO {

    private OffsetDateTime updatedDate;

    private Status status;

}
