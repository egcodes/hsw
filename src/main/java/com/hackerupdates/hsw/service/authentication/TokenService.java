package com.hackerupdates.hsw.service.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public String get(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public void remove(String token) {
        redisTemplate.opsForValue().getAndDelete(token);
    }

    public void set(String token, String personId) {
        redisTemplate.opsForValue().set(token, personId, Duration.ofDays(90));
    }

}