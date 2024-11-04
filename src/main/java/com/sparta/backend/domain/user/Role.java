package com.sparta.backend.domain.user;

import java.util.Arrays;

public enum Role {
    USER, OWNER;

    public static Role from(final String role) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow();
    }
}
