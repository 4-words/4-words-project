package com.sparta.backend.service.menu;

import com.sparta.backend.client.S3FileUploader;
import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import com.sparta.backend.domain.store.StoreStatus;
import com.sparta.backend.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {


    @Mock
    private MenuRepository menuRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private S3FileUploader fileUploader;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("메뉴 생성 시 가게가 존재하지 않을 때")
    void STORE_NOT_FOUND() {

        // given
        final LocalTime openedAt = LocalTime.of(
                14,
                23,
                24,
                4);

        final LocalTime closedAt = LocalTime.of(
                20,
                23,
                24,
                4);

        final User user = User.of(
                "테스트 이메일",
                "테스트 패스워드",
                "테스트 유저",
                "테스트 주소",
                "테스트 이미지",
                "owner");

        final Store store = Store.of(user,
                "가게 이름",
                "KOREA",
                "가게 사진",
                "가게 소개",
                "가게 주소",
                openedAt,
                closedAt,
                100000);


        final Long storeId = 1L;

        final MultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test file".getBytes()
        );

        final CreateRequest createRequest = new CreateRequest("족발", 10000);

        final Long id = 1L;

        when(storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)).thenReturn(Optional.empty());

        // when - then
        assertThatThrownBy(()->menuService.createMenu(storeId, file, createRequest, id))
                .isInstanceOf(ApplicationException.class);
    }
}