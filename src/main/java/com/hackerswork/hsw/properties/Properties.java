package com.hackerswork.hsw.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hackerswork.client")
@Getter
@Setter
public class Properties {

    private String githubLoginUrl;

    private String githubUserUrl;

    private String githubRedirectUri;

}
