package com.hackerswork.hsw.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth-provider")
@Getter
@Setter
public class AuthProviderProperties {

    private final Github github = new Github();

    @Getter
    @Setter
    public static class Github {
        private String loginUrl;
        private String userUrl;
        private String redirectUrl;
    }

}
