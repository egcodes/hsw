package com.hackerswork.hsw.service.security.impl;

import com.hackerswork.hsw.service.security.TokenManager;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenManagerImpl implements TokenManager {

    private final ConcurrentHashMap<String, String> tokenCache = new ConcurrentHashMap<>();


    @Override
    public String get(String key) {
        return tokenCache.get(key);
    }

    @Override
    public void set(String key, String value) {
        tokenCache.put(key, value);
    }
}
