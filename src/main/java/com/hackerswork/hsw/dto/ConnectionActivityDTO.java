package com.hackerswork.hsw.dto;

import com.hackerswork.hsw.enums.Activity;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionActivityDTO implements Serializable {

    private String name;
    private String userName;
    private Activity activity;
    private boolean pinned;

}
