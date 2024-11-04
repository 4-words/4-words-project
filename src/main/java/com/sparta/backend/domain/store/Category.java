package com.sparta.backend.domain.store;

import java.util.Arrays;

public enum Category {
    KOREA, JAPAN, CHINA;

    public static Category from(final String category) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(category))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이 카테고리는 존재하지 않습니다."));
    }
}
