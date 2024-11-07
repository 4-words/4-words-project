package com.sparta.backend.service.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.common.jwt.JwtProvider;
import com.sparta.backend.config.PasswordEncoder;
import com.sparta.backend.controller.user.dto.JoinRequest;
import com.sparta.backend.controller.user.dto.LoginRequest;
import com.sparta.backend.controller.user.dto.LoginResponse;
import com.sparta.backend.domain.user.Role;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("정상적으로 회원가입이 된다.")
    void join_test_success() {
        // given
        final JoinRequest req = new JoinRequest("테스트 유저", "테스트 패스워드", "테스트 이메일",
                "테스트 주소", "테스트 이미지", "user");

        final User user = User.of(1L, "테스트 이메일", "테스트 패스워드", "테스트 유저", "테스트 주소"
                , "테스트 이미지", "user");

        when(passwordEncoder.encode("테스트 패스워드")).thenReturn("테스트 패스워드");
        when(userRepository.save(any())).thenReturn(user);

        // when
        final Long savedId = authService.join(req);

        // then
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    @DisplayName("실패 - 유저 이메일이 존재하지 않을 경우")
    void login_test_invalid_user_email() {
        // given
        final LoginRequest req = new LoginRequest("테스트 이메일", "테스트 패스워드");

        when(userRepository.findByEmail("테스트 이메일")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.login(req)).isInstanceOf(ApplicationException.class);
    }

    @Test
    @DisplayName("실패 - 패스워드가 올바르지 않을 경우")
    void login_test_invalid_password() {
        // given
        final LoginRequest req = new LoginRequest("테스트 이메일", "테스트 패스워드");
        final User user = User.of(1L, "테스트 이메일", "테스트 패스워드", "테스트 유저", "테스트 주소"
                , "테스트 이미지", "user");
        when(userRepository.findByEmail("테스트 이메일")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("테스트 패스워드", "테스트 패스워드")).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(req)).isInstanceOf(ApplicationException.class);
    }

    @Test
    @DisplayName("정상 - 정상적으로 로그인이 될 경우")
    void login_test_success() {
        // given
        final LoginRequest req = new LoginRequest("테스트 이메일", "테스트 패스워드");
        final User user = User.of(1L, "테스트 이메일", "테스트 패스워드", "테스트 유저", "테스트 주소"
                , "테스트 이미지", "user");

        when(userRepository.findByEmail("테스트 이메일")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("테스트 패스워드", "테스트 패스워드")).thenReturn(true);
        when(jwtProvider.createToken(1L, Role.USER)).thenReturn("테스트 토큰");

        // when
        final LoginResponse resp = authService.login(req);

        // then
        assertThat(resp.token()).isEqualTo("테스트 토큰");
    }
}
