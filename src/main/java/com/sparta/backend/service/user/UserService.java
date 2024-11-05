package com.sparta.backend.service.user;

import static com.sparta.backend.common.ErrorCodes.USER_NOT_FOUND;

import com.sparta.backend.client.S3FileUploader;
import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.common.ErrorCodes;
import com.sparta.backend.controller.user.dto.UserUpdateRequest;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3FileUploader fileUploader;

    @Transactional
    public void updateImage(final Long userId, final MultipartFile image, final Long loginId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!user.isOwner(loginId)) {
            throw new ApplicationException(ErrorCodes.USER_NOT_OWNER, HttpStatus.FORBIDDEN);
        }

        final String imageUrl = fileUploader.uploadFiles(image);
        user.update(imageUrl);
    }

    @Transactional
    public void updateProfile(final Long userId, final UserUpdateRequest req, final Long loginId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!user.isOwner(loginId)) {
            throw new ApplicationException(ErrorCodes.USER_NOT_OWNER, HttpStatus.FORBIDDEN);
        }

        user.updateProfile(req.name(), req.password(), req.email(), req.address(), req.role());
    }
}
