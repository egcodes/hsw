package com.hackerupdates.hsw.dto;

import com.hackerupdates.hsw.enums.Activity;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionActivityDTO implements Serializable {

    private Long personId;
    private String userName;
    private String name;
    private Activity activity;
    private boolean pinned;

}
