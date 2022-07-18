package com.hackerswork.hsw.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileDTO {

    private String following;

    private String follower;

    private String blocked;

    private String hidden;

}
