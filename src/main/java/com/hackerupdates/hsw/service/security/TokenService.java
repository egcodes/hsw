package com.hackerupdates.hsw.service.security;

import com.hackerupdates.hsw.constants.Constant;
import com.hackerupdates.hsw.domain.entity.Token;
import com.hackerupdates.hsw.domain.repository.TokenRepository;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Cacheable(Constant.CACHE_NAME_FOR_TOKEN)
    public Token get(String token) {
        return getFromDB(token);
    }

    public Token getFromDB(String token) {
        log.debug("Get stored token for key: {}", token);
        var tokenPossible = tokenRepository.findByToken(token);
        if (tokenPossible.isPresent()) {
            log.debug("Return stored token for key: {}", tokenPossible.get().getToken());
            return tokenPossible.get();
        }
        return null;
    }

    @CachePut(value = Constant.CACHE_NAME_FOR_TOKEN, key = "#token")
    public Token set(Long personId, String userName, String token) {
        var expiredDate = Instant.now().plusSeconds(Constant.COOKIE_EXPIRE_TIME).getEpochSecond();

        var tokenPossible = tokenRepository.findByToken(token);
        Token newToken;
        if (tokenPossible.isPresent()) {
            newToken = tokenPossible.get();
            newToken.setToken(token);
            newToken.setExpireDate(expiredDate);
        } else {
            newToken = Token.builder()
                .personId(personId)
                .userName(userName)
                .token(token)
                .expireDate(expiredDate)
                .build();
        }

        tokenRepository.save(newToken);
        log.debug("Cache & save token for key: {}, value: {}", token,  newToken);

        return newToken;
    }

    @CacheEvict(Constant.CACHE_NAME_FOR_TOKEN)
    public boolean remove(String token) {
        tokenRepository.deleteById(getFromDB(token).getId());
        log.debug("Evict add delete token for key: {}", token);
        return Boolean.TRUE;
    }

}
