package com.sparta.backend.controller.order;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.order.dto.CreateOrderRequest;
import com.sparta.backend.controller.order.dto.CreateOrderResponse;
import com.sparta.backend.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            @AuthenticationUserId Long id) {
        CreateOrderResponse response = orderService.createOrder(request, id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
