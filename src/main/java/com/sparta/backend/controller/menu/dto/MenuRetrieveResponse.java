package com.sparta.backend.controller.menu.dto;

import com.sparta.backend.domain.menu.Menu;

public record MenuRetrieveResponse(
        Long menuId,
        String name,
        Integer price,
        String image
) {
    public static MenuRetrieveResponse from(final Menu menu) {
        return new MenuRetrieveResponse(menu.getId(), menu.getName(), menu.getPrice(), menu.getImage());
    }
}
