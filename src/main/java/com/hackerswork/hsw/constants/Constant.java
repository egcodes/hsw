package com.hackerswork.hsw.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Constant {

    public static final int INFO_LIMIT_TIME = 400; //ms
    public static final int WARN_LIMIT_TIME = 1000; //ms

    public static final int OFFSET_LIMIT = 16;

    public static final String DATE_FORMAT = "hh:mm a, dd MMM yyyy";

    public static final int DURATION_FOR_ONLINE = 3000 * 60;

    public static final String CACHE_NAME_FOR_TOKEN = "tokens";

    public static final String COOKIE_NAME = "hsw";

    public static final int COOKIE_EXPIRE_TIME = 5 * 365 * 24 * 60 * 60; //5 years

    public static final String PERSON_ID = "person-id";
    public static final String TOKEN = "token";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Path {
        public static final String LOGIN = "/login";
        public static final String TOKEN_VALIDATE = "/validate";
        public static final String COOKIE = "/createCookie";
        public static final String SWAGGER_UI = "/swagger";
        public static final String API_DOCS = "/api-docs";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GithubRequestHeader {
        public static final String CLIENT_ID = "client_id";
        public static final String CLIENT_SECRET = "client_secret";
        public static final String CODE = "code";
        public static final String REDIRECT_URI = "redirect_uri";
    }
}
