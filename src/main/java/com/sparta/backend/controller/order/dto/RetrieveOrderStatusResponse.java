package com.sparta.backend.controller.order.dto;

import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderStatus;
import com.sparta.backend.domain.order.OrderType;
import lombok.Getter;

@Getter
public class RetrieveOrderStatusResponse {
    private String storeName;
    private OrderStatus status;
    private String menuName;

    public RetrieveOrderStatusResponse(Order order) {
        this.storeName = order.getStore().getName();
        this.status = order.getStatus();
        this.menuName = order.getMenu().getName();
    }
}
