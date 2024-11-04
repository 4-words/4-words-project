package com.sparta.backend.controller.menu;

import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.CreateResponse;
import com.sparta.backend.controller.menu.dto.MenuReponse;
import com.sparta.backend.domain.menu.MenuStatus;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.service.menu.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.backend.domain.menu.MenuStatus.INACTIVE;

@RestController
@RequiredArgsConstructor
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





}
