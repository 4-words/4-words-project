package com.sparta.backend.controller.user.dto;

public record JoinRequest(
        String name,
        String password,
        String email,
        String address,
        String image,
        String role
) {
}
