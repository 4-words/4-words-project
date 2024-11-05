package com.sparta.backend.service.user;

import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.controller.user.dto.ProfileResponseDto;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.sparta.backend.common.ErrorCodes.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ProfileResponseDto getProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(user);
        return profileResponseDto;
    }
}
