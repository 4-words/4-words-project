package com.sparta.backend.service.auth;

import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.common.jwt.JwtProvider;
import com.sparta.backend.config.PasswordEncoder;
import com.sparta.backend.controller.user.dto.JoinRequest;
import com.sparta.backend.controller.user.dto.LoginRequest;
import com.sparta.backend.controller.user.dto.LoginResponse;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.sparta.backend.common.ErrorCodes.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Long join(final JoinRequest req) {
        final String password = passwordEncoder.encode(req.password());
        final User user = User.of(req.email(), password, req.name(), req.address(), req.image(), req.role());
        final User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public LoginResponse login(final LoginRequest request) {
        final User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApplicationException(INVALID_EMAIL, HttpStatus.NOT_FOUND));

        if (!user.isValidPassword(request.password(), passwordEncoder)) {
            throw new ApplicationException(INVALID_PASSWORD, HttpStatus.FORBIDDEN);
        }

        final String token = jwtProvider.createToken(user.getId(), user.getRole());
        return new LoginResponse(token);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
