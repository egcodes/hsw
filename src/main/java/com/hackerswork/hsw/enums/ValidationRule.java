package com.hackerswork.hsw.enums;

import lombok.Getter;

@Getter
public enum ValidationRule {

    PERSON_NOT_FOUND("Person not found");

    private final String error;

    ValidationRule(String error) {
        this.error = error;
    }
}
