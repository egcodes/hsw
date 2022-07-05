package com.hackerswork.hsw.service.security;

public interface TokenService {

    String get(String key);

    String set(String key, String value);

}
