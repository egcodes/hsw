package com.hackerupdates.hsw.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileDTO {

    private PersonSumDTO following;

    private PersonSumDTO follower;

    private PersonSumDTO blocked;

    private PersonSumDTO hidden;

}
