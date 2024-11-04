package com.sparta.backend.controller.store;

import com.sparta.backend.controller.store.dto.StoreRetrieveResponse;
import com.sparta.backend.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreRetrieveResponse> retrieve(@PathVariable final Long storeId) {
        final StoreRetrieveResponse resp = storeService.retrieve(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
