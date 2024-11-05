package com.sparta.backend.controller.order.dto;

import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderType;

public class RetrieveOrderStatusResponse {
    private String storeName;
    private OrderType orderType;
    private String menuName;

    public RetrieveOrderStatusResponse(Order order) {
        this.storeName = order.getStore().getName();
        this.orderType = order.getType();
        this.menuName = order.getMenu().getName();
    }
}
