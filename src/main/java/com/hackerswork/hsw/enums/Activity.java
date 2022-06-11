package com.hackerswork.hsw.enums;

import static com.hackerswork.hsw.constants.Constant.DURATION_FOR_ONLINE;

import java.time.Instant;

public enum Activity {
    OFFLINE, BUSY, ONLINE;

    public static Activity findBy(Long lastActivityTime) {
        var checkTime = Instant.now().minusMillis(DURATION_FOR_ONLINE).toEpochMilli();
        if (lastActivityTime > checkTime)
            return ONLINE;
        return OFFLINE;
    }
}