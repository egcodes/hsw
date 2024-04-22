package com.hackerupdates.hsw.enums;

import lombok.Getter;

@Getter
public enum ValidationRule {

    PERSON_NOT_FOUND("Person not found"),
    PERSON_ALREADY_EXISTS("You cannot register with this username. Already registered"),
    SHARE_NOT_FOUND("Share not found"),
    SHARE_IS_BLANK("Share is blank"),
    COULD_NOT_SIGN_IN("Could not sign in"),
    INVALID_TOKEN("Invalid token"),
    INVALID_USER_OR_PASSWORD("Username or password is incorrect"),
    TOO_MANY_REQUESTS("Too many requests"),
    UNAUTHORIZED_ACCESS("Unauthorized access");

    private final String error;

    ValidationRule(String error) {
        this.error = error;
    }
}
