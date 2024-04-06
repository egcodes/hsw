package com.hackerupdates.hsw.service.security;

import com.hackerupdates.hsw.persistence.entity.Token;

public interface TokenService {

    Token get(String token);

    Token getFromDB(String token);

    Token set(Long personId, String userName, String token);

    boolean remove(String token);

}
