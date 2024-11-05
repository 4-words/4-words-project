package com.sparta.backend.service.store;

import static com.sparta.backend.common.ErrorCodes.USER_NOT_FOUND;

import com.sparta.backend.client.S3FileUploader;
import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.controller.menu.dto.MenuRetrieveResponse;
import com.sparta.backend.controller.store.dto.StoreCreateRequest;
import com.sparta.backend.controller.store.dto.StoreRetrieveResponse;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.menu.MenuStatus;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import com.sparta.backend.domain.store.dto.StoreRetrieveResponseByCategory;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final S3FileUploader fileUploader;

    public Long create(final Long userId, final StoreCreateRequest req, final MultipartFile image) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        final String imageUrl = fileUploader.uploadFiles(image);
        final Store store = Store.of(user, req.name(), req.category(), imageUrl, req.introduce(), req.address(),
                req.openedAt(), req.closedAt(), req.minOrderPrice());
        final Store savedStore = storeRepository.save(store);
        return savedStore.getId();
    }

    public StoreRetrieveResponse retrieve(final Long storeId) {
        final Store store = storeRepository.findById(storeId).orElseThrow();
        final List<Menu> menus = menuRepository.findByStoreIdAndStatus(storeId, MenuStatus.ACTIVE);

        final List<MenuRetrieveResponse> menuResponses = menus.stream()
                .map(MenuRetrieveResponse::from)
                .toList();

        return StoreRetrieveResponse.of(store, menuResponses);
    }

    public Page<StoreRetrieveResponseByCategory> retrieveByCategory(
            final String type,
            final int page,
            final int limit
    ) {
        return storeRepository.retrieveByCategory(type,
                PageRequest.of(page, limit));
    }
}
