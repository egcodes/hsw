package com.hackerswork.hsw.dto;

import com.hackerswork.hsw.enums.Activity;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionActivityDTO implements Serializable {

    private String userName;
    private String name;
    private Activity activity;

}
