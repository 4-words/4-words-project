package com.sparta.backend.controller.auth;

import com.sparta.backend.controller.user.dto.JoinRequest;
import com.sparta.backend.controller.user.dto.LoginRequest;
import com.sparta.backend.controller.user.dto.LoginResponse;
import com.sparta.backend.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<Long> signup(@RequestBody final JoinRequest req) {
        final Long userId = authService.join(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody final LoginRequest request,
            final HttpServletResponse response
    ) {
        final LoginResponse loginResponse = authService.login(request);
        response.addHeader("Authorization", "Bearer " + loginResponse.token());
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
