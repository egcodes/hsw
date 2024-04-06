package com.hackerupdates.hsw.exception;

import com.hackerupdates.hsw.enums.ValidationRule;

public class HswException extends RuntimeException {

    public HswException(ValidationRule rule) {
        super(rule.getError());
    }

}
