package com.hackerswork.hsw.enums;

public enum Status {
    WAITING_FOR_APPROVAL("Waiting For Approval"),
    ACTIVE("Active"),
    DEACTIVATE("Deactive");

    private String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

}