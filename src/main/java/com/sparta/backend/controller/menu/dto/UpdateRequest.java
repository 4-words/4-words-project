package com.sparta.backend.controller.menu.dto;

import lombok.Getter;

@Getter
public class UpdateRequest {
    private String name;
    private Integer price;


    public UpdateRequest(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
