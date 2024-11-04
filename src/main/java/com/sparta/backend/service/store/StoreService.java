package com.sparta.backend.service.store;

import com.sparta.backend.controller.menu.dto.MenuRetrieveResponse;
import com.sparta.backend.controller.store.dto.StoreRetrieveResponse;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.menu.MenuStatus;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public StoreRetrieveResponse retrieve(final Long storeId) {
        final Store store = storeRepository.findById(storeId).orElseThrow();
        final List<Menu> menus = menuRepository.findByStoreIdAndStatus(storeId, MenuStatus.ACTIVE);

        final List<MenuRetrieveResponse> menuResponses = menus.stream()
                .map(MenuRetrieveResponse::from)
                .toList();

        return StoreRetrieveResponse.of(store, menuResponses);
    }
}
