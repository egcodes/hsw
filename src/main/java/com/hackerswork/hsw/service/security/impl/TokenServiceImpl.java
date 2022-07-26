package com.hackerswork.hsw.service.security.impl;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.persistence.entity.Token;
import com.hackerswork.hsw.persistence.repository.TokenRepository;
import com.hackerswork.hsw.service.security.TokenService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    @Cacheable(Constant.CACHE_NAME_FOR_TOKEN)
    public Token get(String token) {
        return getFromDB(token);
    }

    @Override
    public Token getFromDB(String token) {
        log.debug("Get persisted token for key: {}", token);
        var tokenPossible = tokenRepository.findByToken(token);
        if (tokenPossible.isPresent()) {
            log.debug("Return persisted token for key: {}", tokenPossible.get().getToken());
            return tokenPossible.get();
        }
        return null;
    }

    @Override
    @CachePut(value= Constant.CACHE_NAME_FOR_TOKEN, key = "#token")
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

    @Override
    @CacheEvict(value= Constant.CACHE_NAME_FOR_TOKEN)
    public boolean remove(String token) {
        tokenRepository.deleteById(getFromDB(token).getId());
        log.debug("Evict add delete token for key: {}", token);
        return Boolean.TRUE;
    }

}
