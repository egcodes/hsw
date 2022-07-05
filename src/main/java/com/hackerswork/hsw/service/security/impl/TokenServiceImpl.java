package com.hackerswork.hsw.service.security.impl;

import com.hackerswork.hsw.constants.Constant;
import com.hackerswork.hsw.persistence.entity.Token;
import com.hackerswork.hsw.persistence.repository.TokenRepository;
import com.hackerswork.hsw.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String get(String key) {
        log.info("Get persisted token for key: {}", key);
        var tokenPossible = tokenRepository.findByUserName(key);
        if (tokenPossible.isPresent()) {
            log.info("Return persisted token for key: {}, value: {}", key, tokenPossible.get().getText());
            return tokenPossible.get().getText();
        }
        return null;
    }

    @Override
    @CachePut(value= Constant.CACHE_NAME_FOR_TOKEN, key="#key")
    public String set(String key, String value) {
        log.info("Cache & save token for key: {}, value: {}", key, value);

        var tokenPossible = tokenRepository.findByUserName(key);
        Token token;
        if (tokenPossible.isPresent()) {
            token = tokenPossible.get();
            token.setText(value);
        } else {
            token = Token.builder().userName(key).text(value).build();
        }

        tokenRepository.save(token);
        return token.getText();
    }
}
