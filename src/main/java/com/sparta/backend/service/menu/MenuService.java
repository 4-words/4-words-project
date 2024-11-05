package com.sparta.backend.service.menu;

import com.sparta.backend.client.S3FileUploader;
import com.sparta.backend.common.ApplicationException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.sparta.backend.common.ErrorCodes.*;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final S3FileUploader fileUploader;

    @Transactional
    public CreateResponse createMenu(Long storeId, CreateRequest request, Long id, MultipartFile image) {
        Store store = storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)
                .orElseThrow(() -> new ApplicationException(STORE_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!store.isOwner(id)) {
            throw new ApplicationException(STORE_NOT_OWNER, HttpStatus.UNAUTHORIZED);
        }

        String imageUrl = fileUploader.uploadFiles(image);
        Menu menu = new Menu(store, request, MenuStatus.ACTIVE, imageUrl);
        menuRepository.save(menu);

        return new CreateResponse(menu);
    }

    @Transactional
    public UpdateResponse updateMenu(Long storeId, Long menuId, UpdateRequest updateRequest, Long id) {
        Store store = storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)
                .orElseThrow(() -> new ApplicationException(STORE_NOT_FOUND, HttpStatus.NOT_FOUND));

        Menu menu = menuRepository.findByIdAndStatus(menuId, MenuStatus.ACTIVE)
                .orElseThrow(() -> new ApplicationException(MENU_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!store.isOwner(id)) {
            throw new ApplicationException(STORE_NOT_OWNER, HttpStatus.UNAUTHORIZED);
        }

        String name = updateRequest.getName();
        Integer price = updateRequest.getPrice();
        menu.updated(name, price);
        menuRepository.saveAndFlush(menu);

        return new UpdateResponse(menu);
    }

    @Transactional
    public void deleteMenu(Long storeId, Long menuId, Long id) {
        Store store = storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)
                .orElseThrow(() -> new ApplicationException(STORE_NOT_FOUND, HttpStatus.NOT_FOUND));

        Menu menu = menuRepository.findByIdAndStatus(menuId, MenuStatus.ACTIVE)
                .orElseThrow(() -> new ApplicationException(MENU_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!store.isOwner(id)) {
            throw new ApplicationException(STORE_NOT_OWNER, HttpStatus.UNAUTHORIZED);
        }

        menu.delete(MenuStatus.INACTIVE);
        menuRepository.saveAndFlush(menu);
    }
}
