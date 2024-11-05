package com.sparta.backend.controller.order;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.order.dto.CreateOrderRequest;
import com.sparta.backend.controller.order.dto.CreateOrderResponse;
import com.sparta.backend.controller.order.dto.RetrieveOrderListResponse;
import com.sparta.backend.controller.order.dto.RetrieveOrderStatusResponse;
import com.sparta.backend.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{storeId}/order/{menuId}")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody CreateOrderRequest request,
            @AuthenticationUserId Long id) {
        CreateOrderResponse response = orderService.createOrder(storeId, menuId, request, id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/profile/orders")
    public ResponseEntity<List<RetrieveOrderListResponse>> retrieveOrders(@AuthenticationUserId Long id) {
    List<RetrieveOrderListResponse> orders = orderService.retrieveOrder(id);
    return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/status")
    public ResponseEntity<List<RetrieveOrderStatusResponse>> retrieveOrderStatus(@AuthenticationUserId Long id) {
        List<RetrieveOrderStatusResponse> orders = orderService.retrieveOrderStatus(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<String> acceptOrder(@PathVariable Long orderId, @AuthenticationUserId Long id) {
        orderService.acceptOrder(orderId, id);
        return new ResponseEntity<>("주문수락", HttpStatus.OK);
    }
}
