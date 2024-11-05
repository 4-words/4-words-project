package com.sparta.backend.controller.user;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.user.dto.UserUpdateRequest;
import com.sparta.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/user")
public class UserController {

    private final UserService userService;

    @PutMapping("/{userId}/image")
    public ResponseEntity<Void> updateImage(
            @PathVariable final Long userId,
            @RequestPart final MultipartFile image,
            @AuthenticationUserId final Long loginId
    ) {
        userService.updateImage(userId, image, loginId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateProfile(
            @PathVariable final Long userId,
            @RequestBody final UserUpdateRequest req,
            @AuthenticationUserId final Long loginId
    ) {
        userService.updateProfile(userId, req, loginId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
