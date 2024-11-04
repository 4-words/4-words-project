package com.sparta.backend.controller.menu;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.CreateResponse;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.service.menu.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/{storeId}")
    public ResponseEntity<CreateResponse> createMenu(@PathVariable Long storeId,
                                                     @RequestBody @Valid CreateRequest response,
                                                     @AuthenticationUserId Long id) {

        CreateResponse createResponse = menuService.createMenu(storeId, response, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

}
