package com.sparta.backend.controller.menu.dto;

import com.sparta.backend.domain.menu.Menu;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateResponse {
    private Long id;
    private Long storeId;
    private String name;
    private Integer price;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CreateResponse(Menu menu) {
        this.id = menu.getId();
        this.storeId = menu.getStore().getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.createdAt = menu.getCreatedAt();
        this.modifiedAt = menu.getModifiedAt();
    }
}
