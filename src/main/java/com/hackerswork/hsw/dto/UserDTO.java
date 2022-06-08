package com.hackerswork.hsw.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDTO {

    private String login;
    private String name;
    private String email;
    private String followersUrl;
    private String followingUrl;

}
