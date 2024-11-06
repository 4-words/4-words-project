package com.sparta.backend.service.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.sparta.backend.client.S3FileUploader;
import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.store.dto.StoreCreateRequest;
import com.sparta.backend.controller.store.dto.StoreRetrieveResponse;
import com.sparta.backend.controller.store.dto.StoreUpdateRequest;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.menu.MenuStatus;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import com.sparta.backend.domain.store.StoreStatus;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private S3FileUploader fileUploader;

    @InjectMocks
    private StoreService storeService;

    @Test
    @DisplayName("가게를 생성할 때 유저가 유효하지 않은 경우")
    void create_store_user_not_found() {
        // given
        final Long userId = 1L;
        final StoreCreateRequest req = new StoreCreateRequest(
                "test",
                "korea",
                "test introduce",
                "test address",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1000
        );
        final MultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test file".getBytes()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> storeService.create(userId, req, file)).
                isInstanceOf(ApplicationException.class);
    }

    @Test
    @DisplayName("가게를 단건 조회할 때 가게가 존재하지 않은 경우")
    void retrieve_store_test() {
        // given
        final Long storeId = 1L;
        final StoreStatus status = StoreStatus.ACTIVE;

        when(storeRepository.findByIdAndStatus(storeId, status)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> storeService.retrieve(storeId))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    @DisplayName("정상 - 가게를 단건 조회")
    void retrieve_store_success() {
        // given
        final Long storeId = 1L;
        final StoreStatus status = StoreStatus.ACTIVE;
        final LocalDateTime openedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);
        final LocalDateTime closedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);

        final User user = User.of("test email", "test password", "test name", "test address"
                , "test image", "user");
        final Store store = Store.of(user, "storeName", "korea", "test image", "test introduce", "test address",
                openedAt, closedAt, 1000);
        final CreateRequest createRequest = new CreateRequest("test name", 1000);
        final CreateRequest createRequest1 = new CreateRequest("test name1", 2000);

        final Menu menu = new Menu(store, createRequest, "test image");
        final Menu menu1 = new Menu(store, createRequest1, "test image1");

        when(storeRepository.findByIdAndStatus(storeId, status)).thenReturn(Optional.of(store));
        when(menuRepository.findByStoreIdAndStatus(storeId, MenuStatus.ACTIVE)).thenReturn(List.of(menu, menu1));

        // when
        final StoreRetrieveResponse resp = storeService.retrieve(storeId);

        // then
        assertThat(resp.name()).isEqualTo("storeName");
        assertThat(resp.category()).isEqualTo("KOREA");
        assertThat(resp.image()).isEqualTo("test image");
        assertThat(resp.introduce()).isEqualTo("test introduce");
        assertThat(resp.openedAt()).isEqualTo(openedAt);
        assertThat(resp.closedAt()).isEqualTo(closedAt);
        assertThat(resp.minOrderPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("가게를 수정할 때 가게가 존재하지 않을 경우")
    void update_test() {
        // given
        final Long storeId = 1L;
        final LocalDateTime openedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);
        final LocalDateTime closedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);

        final StoreUpdateRequest req = new StoreUpdateRequest("storeName", "korea", "test introduce", "test address",
                openedAt, closedAt, 1000);

        final MultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test file".getBytes()
        );

        when(storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> storeService.update(1L, 1L, req, file)).isInstanceOf(ApplicationException.class);
    }

    @Test
    @DisplayName("정상 - 가게가 성공적으로 수정되는 경우")
    void update_test_success() {
        // given
        final Long storeId = 1L;
        final Long loginId = 1L;
        final LocalDateTime openedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);
        final LocalDateTime closedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);

        final StoreUpdateRequest req = new StoreUpdateRequest("updateStoreName", "korea", "update introduce",
                "update address",
                openedAt, closedAt, 2000);

        final User user = User.of(1L, "test email", "test password", "test name", "test address"
                , "test image", "user");
        final Store store = Store.of(user, "storeName", "korea", "test image", "test introduce", "test address",
                openedAt, closedAt, 1000);

        final MultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test file".getBytes()
        );
        final String image = "testMultiImage";

        when(storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)).thenReturn(Optional.of(store));
        when(fileUploader.uploadFiles(file)).thenReturn(image);

        // when
        storeService.update(loginId, 1L, req, file);

        // then
        assertThat(store.getName()).isEqualTo("updateStoreName");
        assertThat(store.getCategory().name()).isEqualTo("KOREA");
        assertThat(store.getIntroduce()).isEqualTo("update introduce");
        assertThat(store.getAddress()).isEqualTo("update address");
        assertThat(store.getOpenedAt()).isEqualTo(openedAt);
        assertThat(store.getClosedAt()).isEqualTo(closedAt);
        assertThat(store.getMinOrderPrice()).isEqualTo(2000);
    }

    @Test
    @DisplayName("실패 - 가게의 주인이 아닌경우")
    void update_test_not_owner() {
        // given
        final Long storeId = 1L;
        final Long loginId = 2L;
        final LocalDateTime openedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);
        final LocalDateTime closedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);

        final StoreUpdateRequest req = new StoreUpdateRequest("updateStoreName", "korea", "update introduce",
                "update address",
                openedAt, closedAt, 2000);

        final User user = User.of(1L, "test email", "test password", "test name", "test address"
                , "test image", "user");
        final Store store = Store.of(user, "storeName", "korea", "test image", "test introduce", "test address",
                openedAt, closedAt, 1000);

        final MultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test file".getBytes()
        );

        when(storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)).thenReturn(Optional.of(store));

        // when & then
        assertThatThrownBy(() -> storeService.update(loginId, 1L, req, file)).isInstanceOf(
                ApplicationException.class);
    }
}
