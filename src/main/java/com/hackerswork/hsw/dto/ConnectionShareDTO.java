package com.hackerswork.hsw.dto;

import com.hackerswork.hsw.persistence.entity.Share;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionShareDTO implements Serializable {

    private String name;
    private String userName;
    private Share share;

}
