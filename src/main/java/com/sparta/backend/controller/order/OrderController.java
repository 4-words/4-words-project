package com.sparta.backend.controller.order;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.order.dto.CreateOrderRequest;
import com.sparta.backend.controller.order.dto.CreateOrderResponse;
import com.sparta.backend.domain.order.OrderRepository;
import com.sparta.backend.domain.user.User;
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


    // 1. 주문하기
    @PostMapping("/order")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            @AuthenticationUserId User user) {

        CreateOrderResponse response = orderService.createOrder(request, user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





    // 2. 주문 내역 조회



    // 3. 주문 상태 조회



    // 4. 주문 취소하기



}
