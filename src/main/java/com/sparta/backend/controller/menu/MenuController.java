package com.sparta.backend.controller.menu;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.CreateResponse;
import com.sparta.backend.controller.menu.dto.UpdateRequest;
import com.sparta.backend.controller.menu.dto.UpdateResponse;
import com.sparta.backend.service.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("stores/{storeId}/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CreateResponse> createMenu(
            @PathVariable Long storeId,
            @RequestPart MultipartFile image,
            @RequestPart @Valid CreateRequest request,
            @AuthenticationUserId Long id
    ) {
        CreateResponse createResponse = menuService.createMenu(storeId, image, request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<UpdateResponse> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody UpdateRequest updateRequest,
            @AuthenticationUserId Long id
    ) {
        UpdateResponse response = menuService.updateMenu(storeId, menuId, updateRequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @AuthenticationUserId Long id
    ) {
        menuService.deleteMenu(storeId, menuId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("메뉴 삭제 완료");
    }

    @DeleteMapping("/{menuId}/image")
    public ResponseEntity<String> deleteMenuImage(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @AuthenticationUserId Long id
    ) {
        menuService.deleteMenuImage(storeId, menuId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("메뉴 이미지 삭제 완료");
    }
}

