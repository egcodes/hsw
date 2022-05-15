package com.hackerswork.hsw.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "microservice.test")
@Getter
@Setter
public class Properties {

    private int test;

}
