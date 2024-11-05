package com.sparta.backend.controller.order.dto;

import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderType;

import java.time.LocalDateTime;

public class RetrieveOrderListResponse {
    private Long orderId;
    private LocalDateTime orderTime; // 조회 시 사용될 주문 시각
    private OrderType orderType;
    private String menuImage;
    private String menuName;
    private int totalPrice;
    private String storeName;

    public RetrieveOrderListResponse(Order order) {
        this.orderId = order.getId();
        this.orderType = order.getType();
        this.storeName = order.getStore().getName();
        this.menuImage = order.getMenu().getImage();
        this.menuName = order.getMenu().getName();
        this.totalPrice = order.getTotalPrice();
        this.orderTime = order.getCreatedAt();
    }


}
