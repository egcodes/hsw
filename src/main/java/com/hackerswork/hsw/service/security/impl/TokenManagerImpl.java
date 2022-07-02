package com.hackerswork.hsw.service.security.impl;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.service.security.TokenManager;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenManagerImpl implements TokenManager {

    private final ConcurrentHashMap<String, String> tokenCache = new ConcurrentHashMap<>();


    @Override
    @Cacheable("tokens")
    public String get(String key) {
        return tokenCache.get(key);
    }

    @Override
    @CachePut(value= Constant.CACHE_NAME_FOR_TOKEN, key="#key")
    public String set(String key, String value) {
        tokenCache.put(key, value);
        return value;
    }
}
