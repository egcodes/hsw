package com.hackerswork.hsw.service;

import com.hackerswork.hsw.dto.SignUpDTO;
import com.hackerswork.hsw.exception.HswException;
import com.hackerswork.hsw.service.authentication.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitService {

    private final Authentication authentication;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            authentication.signUp(SignUpDTO.builder()
                    .userName("testuser")
                    .mail("testuser@hackerswork.com")
                    .password("12345678")
                    .build());

            authentication.signUp(SignUpDTO.builder()
                    .userName("hackers-work")
                    .mail("info@hackerswork.com")
                    .password("12345678")
                    .build());
        } catch (HswException ignore) {
        }
    }
}
