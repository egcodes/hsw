package com.hackerswork.hsw.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GithubUserDTO {

    private String login;
    private String name;
    private String email;
    private String followersUrl;
    private String followingUrl;

}
