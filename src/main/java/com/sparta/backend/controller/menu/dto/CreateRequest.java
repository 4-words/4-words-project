package com.sparta.backend.controller.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;

import java.awt.*;

@Getter
public class CreateRequest {
    @NotBlank
    private String name;
    @NonNull
    private Integer price;
}
