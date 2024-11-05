package com.sparta.backend.controller.menu;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.CreateResponse;
import com.sparta.backend.service.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("stores/{storeId}/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CreateResponse> createMenu(
            @PathVariable Long storeId,
            @RequestBody @Valid CreateRequest request,
            @AuthenticationUserId Long id
    ) {
        CreateResponse createResponse = menuService.createMenu(storeId, request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }
}
