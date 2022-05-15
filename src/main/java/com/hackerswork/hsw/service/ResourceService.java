package com.hackerswork.hsw.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Slf4j
public class ResourceService {

    private List<String> badWordList = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    public void getResource() {
        try {

        } catch (Exception e) {
        }
    }

}
