package com.sparta.backend.controller.store.dto;

import com.sparta.backend.controller.menu.dto.MenuRetrieveResponse;
import com.sparta.backend.domain.store.Store;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record StoreRetrieveResponse(
        Long id,
        String name,
        String category,
        String image,
        String introduce,
        String address,
        LocalTime openedAt,
        LocalTime closedAt,
        List<MenuRetrieveResponse> menus,
        Integer minOrderPrice
) {

    public static StoreRetrieveResponse of(final Store store, final List<MenuRetrieveResponse> menus) {
        return new StoreRetrieveResponse(store.getId(), store.getName(), store.getCategory().name(), store.getImage(),
                store.getIntroduce(), store.getAddress(), store.getOpenedAt(), store.getClosedAt(), menus,
                store.getMinOrderPrice());
    }
}
