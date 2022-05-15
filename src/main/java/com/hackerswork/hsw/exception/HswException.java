package com.hackerswork.hsw.exception;

import com.hackerswork.hsw.enums.ValidationRule;

public class HswException extends RuntimeException {

    public HswException(ValidationRule rule) {
        super(rule.getError());
    }

}
