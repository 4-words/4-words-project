package com.sparta.backend.service.menu;

import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.common.ErrorCodes;
import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.CreateResponse;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import com.sparta.backend.domain.store.StoreStatus;
import com.sparta.backend.domain.user.Role;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.backend.common.ErrorCodes.*;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateResponse createMenu(Long storeId, CreateRequest request, Long id) {
        Store store = storeRepository.findByIdAndStatus(storeId, StoreStatus.ACTIVE)
                .orElseThrow(() -> new ApplicationException(STORE_NOT_FOUND, HttpStatus.NOT_FOUND));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!user.getId().equals(store.getUser().getId())) {
            throw new ApplicationException(STORE_NOT_OWNER, HttpStatus.UNAUTHORIZED);
        }

        Menu menu = new Menu(store, request);
        menuRepository.save(menu);

        return new CreateResponse(menu);
    }

}
