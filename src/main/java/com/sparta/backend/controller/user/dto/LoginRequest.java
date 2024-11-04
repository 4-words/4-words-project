package com.sparta.backend.controller.user.dto;

public record LoginRequest (
        String email,
        String password
){
}
