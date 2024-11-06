package com.sparta.backend.controller.order.dto;

import com.sparta.backend.domain.order.OrderType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateOrderRequest {

    private OrderType type;
    private Integer totalPrice;
}
