package com.sparta.backend.controller.store.dto;

import java.time.LocalTime;

public record StoreCreateRequest(
        String name,
        String category,
        String introduce,
        String address,
        String openedAt,
        String closedAt,
        Integer minOrderPrice
) {
}
