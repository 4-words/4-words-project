package com.sparta.backend.controller.order.dto;

import com.sparta.backend.domain.order.OrderType;
import lombok.Getter;

@Getter
public class CreateOrderRequest {

    private Long storeId;
    private Long menuId;
    private OrderType type;
    private Integer totalPrice;


}
