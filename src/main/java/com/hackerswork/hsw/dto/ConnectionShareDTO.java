package com.hackerswork.hsw.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionShareDTO implements Serializable {

    private Long shareId;
    private String name;
    private String userName;
    private ShareRespDTO share;

}
