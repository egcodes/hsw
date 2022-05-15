package com.hackerswork.hsw.enums;

import lombok.Getter;

@Getter
public enum ValidationRule {

    TITLE_NOT_ALLOWED("There are words that are not allowed in the title"),
    CATEGORY_NOT_ALLOWED("There is no such category"),
    UPDATE_DUPLICATE_ADS_NOT_ALLOWED("Duplicate records cannot be updated"),
    PERSON_NOT_FOUND("Person not found");

    private final String error;

    ValidationRule(String error) {
        this.error = error;
    }
}
