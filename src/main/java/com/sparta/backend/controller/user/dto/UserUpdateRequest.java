package com.sparta.backend.controller.user.dto;

public record UserUpdateRequest(
        String name,
        String password,
        String email,
        String address,
        String role
) {
}
