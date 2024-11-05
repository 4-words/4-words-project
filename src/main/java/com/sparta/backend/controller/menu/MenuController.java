package com.sparta.backend.controller.menu;

import com.sparta.backend.controller.menu.dto.*;
import com.sparta.backend.domain.menu.MenuStatus;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.service.menu.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.CreateResponse;
import com.sparta.backend.service.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.backend.domain.menu.MenuStatus.INACTIVE;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("stores/{storeId}/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/{storeId}")
    public ResponseEntity<CreateResponse> createMenu(@PathVariable Long storeId,
                                                     @RequestBody CreateRequest response,
                                                     HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        CreateResponse createResponse = menuService.createMenu(storeId, response, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }


    @PutMapping("/{storeId}/{menuId}")
    public ResponseEntity<UpdateResponse> updateMenu(@PathVariable Long storeId,
                                                     @PathVariable Long menuId,
                                                     @RequestBody UpdateRequest updateRequest,
                                                     HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        UpdateResponse response = menuService.updateMenu(storeId, menuId, updateRequest, user);

        return ResponseEntity.status(HttpStatus.OK).body(response);


    }


    @DeleteMapping("/{storeId}/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long storeId,
                                             @PathVariable Long menuId,
                                             HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        menuService.deleteMenu(storeId, menuId, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("메뉴 삭제 완료");


    }


}
