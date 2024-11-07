package com.sparta.backend.controller.store.dto;

import java.time.LocalTime;

public record StoreUpdateRequest(
        String name,
        String category,
        String introduce,
        String address,
        LocalTime openedAt,
        LocalTime closedAt,
        Integer minOrderPrice
) {
}
