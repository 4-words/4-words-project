package com.sparta.backend.common.filter;

import static com.sparta.backend.common.ErrorCodes.TOKEN_EXPIRED;
import static com.sparta.backend.common.ErrorCodes.TOKEN_NULL_EXCEPTION;

import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.common.jwt.JwtProvider;
import com.sparta.backend.domain.user.Role;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        final String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

        final String requestUri = req.getRequestURI();

        if (requestUri.startsWith("/auth/join") || requestUri.startsWith("/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        if (Objects.isNull(authorizationHeader)) {
            throw new ApplicationException(TOKEN_NULL_EXCEPTION, HttpStatus.UNAUTHORIZED);
        }

        if (jwtProvider.isTokenExpired(authorizationHeader)) {
            throw new ApplicationException(TOKEN_EXPIRED, HttpStatus.FORBIDDEN);
        }

        final Role role = jwtProvider.getUserRole(authorizationHeader);

        req.setAttribute("role", role);

        chain.doFilter(request, response);
    }
}
