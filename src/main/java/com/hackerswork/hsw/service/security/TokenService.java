package com.hackerswork.hsw.service.security;

import com.hackerswork.hsw.persistence.entity.Token;

public interface TokenService {

    Token get(String token);

    Token getFromDB(String token);

    Token set(Long personId, String userName, String token);

    boolean evict(String token);

}
