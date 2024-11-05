package com.sparta.backend.controller.order.dto;

import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderStatus;
import com.sparta.backend.domain.order.OrderType;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class RetrieveOrderListResponse {
    private Long orderId;
    private OrderType orderType;
    private OrderStatus status;
    private String storeName;
    private String menuImage;
    private String menuName;
    private int totalPrice;
    private LocalDateTime orderTime;

    public RetrieveOrderListResponse(Order order) {
        this.orderId = order.getId();
        this.orderType = order.getType();
        this.status = order.getStatus();
        this.storeName = order.getStore().getName();
        this.menuImage = order.getMenu().getImage();
        this.menuName = order.getMenu().getName();
        this.totalPrice = order.getTotalPrice();
        this.orderTime = order.getCreatedAt();
    }


}
