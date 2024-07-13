package com.rc.raceproject.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "common user"),
    ADMIN("ROLE_ADMIN", "common admin"),
    BATCH("ROLE_BATCH", "common batch");
    private final String key;
    private final String title;
}
