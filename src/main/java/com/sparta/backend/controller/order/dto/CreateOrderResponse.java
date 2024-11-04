package com.sparta.backend.controller.order.dto;

import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderType;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CreateOrderResponse {
    private Long id;
    private OrderType orderType;
    private String menuName;
    private Integer totalPrice;
    private String storeName;
    private LocalDateTime createdAt;




    public CreateOrderResponse(Order order) {
        this.id = order.getId();
        this.orderType = order.getType();
        this.menuName = order.getMenu().getName();
        this.totalPrice = order.getTotalPrice();
        this.storeName = order.getStore().getName();
        this.createdAt = order.getCreatedAt();



    }
}
