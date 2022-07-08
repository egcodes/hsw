package com.hackerswork.hsw.dto;

import com.hackerswork.hsw.enums.Status;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonDataDTO {

    private String userName;

    private String name;

    private String mail;

    private Status status;

    private Instant createDate;

    private int following;

    private int followers;

}
