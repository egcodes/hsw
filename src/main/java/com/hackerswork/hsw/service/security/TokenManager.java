package com.hackerswork.hsw.service.security;

public interface TokenManager {

    String get(String key);

    String set(String key, String value);

}
