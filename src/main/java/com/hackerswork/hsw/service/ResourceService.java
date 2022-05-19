package com.hackerswork.hsw.service;

import java.util.HashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Getter
@Slf4j
public class ResourceService {

    private final HashMap<String, String> authList = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void getResource() {
        try {
            var resourceStream = new ClassPathResource("credentials.txt").getInputStream();
            try (var reader = new BufferedReader(new InputStreamReader(resourceStream))) {
                while (reader.ready()) {
                    var keyVal = reader.readLine().split(":");
                    authList.put(keyVal[0].strip(), keyVal[1].strip());
                }
            }
        } catch (Exception e) {
            log.warn("Unable to fetch resource file");
        }
    }

}
