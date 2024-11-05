package com.sparta.backend.controller.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateRequest {
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
}
