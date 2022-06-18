package com.hackerswork.hsw.service.security;

public interface TokenManager {

    String get(String key);

    void set(String key, String value);

}
