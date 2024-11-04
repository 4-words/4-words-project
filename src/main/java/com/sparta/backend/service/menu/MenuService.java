package com.sparta.backend.service.menu;

import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.CreateResponse;
import com.sparta.backend.controller.menu.dto.UpdateRequest;
import com.sparta.backend.controller.menu.dto.UpdateResponse;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.menu.MenuStatus;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import com.sparta.backend.domain.store.StoreStatus;
import com.sparta.backend.domain.user.Role;
import com.sparta.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public CreateResponse createMenu(Long storeId, CreateRequest request, User user) {
        Store store = storeRepository.findByIdAAndStatus(storeId, StoreStatus.ACTIVE).orElseThrow(() ->
                new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));

        if (!user.getId().equals(store.getUser().getId())) {
            throw new SecurityException("해당 가게 주인만 메뉴를 생성할 수 있습니다.");
        }

        if (!user.getRole().equals(Role.OWNER)) {
            throw new SecurityException("해당 권한이 없습니다.");
        }

        Menu menu = new Menu(request);
        menuRepository.save(menu);

        return new CreateResponse(menu);
    }


    @Transactional
    public UpdateResponse updateMenu(Long storeId, Long menuId, UpdateRequest updateRequest, User user) {
        Store store = storeRepository.findByIdAAndStatus(storeId, StoreStatus.ACTIVE).orElseThrow(() ->
                new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));

        Menu menu = menuRepository.findByIdAAndStatus(menuId, MenuStatus.ACTIVE).orElseThrow(() ->
                new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));

        if (!user.getId().equals(store.getUser().getId())) {
            throw new SecurityException("해당 가게 주인만 메뉴를 수정할 수 있습니다.");
        }

        if (!user.getRole().equals(Role.OWNER)) {
            throw new SecurityException("해당 권한이 없습니다.");
        }

        String name = updateRequest.getName();
        Integer price = updateRequest.getPrice();
        menu.updated(name, price);
        menuRepository.saveAndFlush(menu);

        return new UpdateResponse(menu);
    }

    @Transactional
    public void deleteMenu(Long storeId, Long menuId, User user) {
        Store store = storeRepository.findByIdAAndStatus(storeId, StoreStatus.ACTIVE).orElseThrow(() ->
                new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));

        Menu menu = menuRepository.findByIdAAndStatus(menuId, MenuStatus.ACTIVE).orElseThrow(() ->
                new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));

        if (!user.getId().equals(store.getUser().getId())) {
            throw new SecurityException("해당 가게 주인만 메뉴를 수정할 수 있습니다.");
        }

        if (!user.getRole().equals(Role.OWNER)) {
            throw new SecurityException("해당 권한이 없습니다.");
        }

        menu.delete(MenuStatus.INACTIVE);
        menuRepository.saveAndFlush(menu);
    }
}
