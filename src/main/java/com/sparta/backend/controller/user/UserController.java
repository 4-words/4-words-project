package com.sparta.backend.controller.user;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.user.dto.ProfileResponseDto;
import com.sparta.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/myself")
    public ResponseEntity<ProfileResponseDto> getProfile (@AuthenticationUserId Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getProfile(id));
    }
}
