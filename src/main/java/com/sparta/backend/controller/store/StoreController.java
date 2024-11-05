package com.sparta.backend.controller.store;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.store.dto.StoreCreateRequest;
import com.sparta.backend.controller.store.dto.StoreRetrieveResponse;
import com.sparta.backend.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping()
    public ResponseEntity<Long> create(
            @AuthenticationUserId final Long userId,
            @RequestPart final StoreCreateRequest request,
            @RequestPart("image") final MultipartFile image
    ) {
        final Long storeId = storeService.create(userId, request, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(storeId);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreRetrieveResponse> retrieve(@PathVariable final Long storeId) {
        final StoreRetrieveResponse resp = storeService.retrieve(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
