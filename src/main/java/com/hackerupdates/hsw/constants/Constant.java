package com.hackerupdates.hsw.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Constant {

    //Security
    public static final int OFFSET_LIMIT = 50;
    public static final int MAX_REQUESTS_WINDOW_IN_SECONDS = 1;
    public static final int MAX_REQUESTS_PER_WINDOW_INT = 8;

    public static final int DURATION_FOR_ONLINE = 3000 * 60;

    public static final String DATE_FORMAT = "hh:mm a, dd MMM yyyy";

    //Cache
    public static final String CACHE_NAME_FOR_TOKEN = "tokens";

    public static final String COOKIE_NAME = "hsw";

    public static final String EMPTY_STRING = "";

    public static final int COOKIE_EXPIRE_TIME = 5 * 365 * 24 * 60 * 60; //5 years

    public static final String PERSON_ID = "person-id";
    public static final String TOKEN = "token";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Path {
        public static final String HEALTH = "/health";
        public static final String ACTUATOR = "/actuator";
        public static final String LOGIN = "/login";
        public static final String SIGN_UP = "/signUp";
        public static final String SIGN_IN = "/signIn";
        public static final String SIGN = "/sign";
        public static final String TOKEN_VALIDATE = "/validate";
        public static final String COOKIE = "/createCookie";
        public static final String SWAGGER_UI = "/swagger-ui";
        public static final String API_DOCS = "/api-docs";
        public static final String API_ALL_SHARES = "/api/connectionShare/list";
        public static final String API_ALL_SHARES_FROM = "/api/connectionShare/listFrom";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GithubRequestHeader {
        public static final String CLIENT_ID = "client_id";
        public static final String CLIENT_SECRET = "client_secret";
        public static final String CODE = "code";
        public static final String REDIRECT_URI = "redirect_uri";
    }
}
