package com.sparta.backend.controller.user.dto;

import com.sparta.backend.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {

    private String email;
    private String name;
    private String password;
    private String address;
    private String image;

    public ProfileResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.address = user.getAddress();
        this.image = user.getImage();
    }
}
