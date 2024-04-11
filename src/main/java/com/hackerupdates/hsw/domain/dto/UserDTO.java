package com.hackerupdates.hsw.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UserDTO {

    private String login;
    private String password;
    private String name;
    private String email;
    private String followersUrl;
    private String followingUrl;

}
