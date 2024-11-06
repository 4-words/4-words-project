package com.sparta.backend.aop;
import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.common.ErrorCodes;
import com.sparta.backend.controller.order.dto.CreateOrderResponse;
import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderRepository;
import com.sparta.backend.domain.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class OrderLoggingAspect {

    private final OrderRepository orderRepository;

    public OrderLoggingAspect(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @AfterReturning(pointcut = "execution(* com.sparta.backend.service.order.OrderService.createOrder(..))",
            returning = "response")
    public void logNewOrder(JoinPoint joinPoint, CreateOrderResponse response) {
        Long storeId = response.getStoreId();
        Long orderId = response.getId();
        LocalDateTime createdAt = response.getCreatedAt();

        log.info("새로운 주문이 생성되었습니다. 주문 요청 시각: {}, 가게 ID: {}, 주문 ID: {}", createdAt, storeId, orderId);
    }

    @AfterReturning("execution(* com.sparta.backend.service.order.OrderService.acceptOrder(..))")
    public void logOrderStatusChange(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long orderId = (Long) args[0];
        LocalDateTime statusChangedAt = LocalDateTime.now();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ApplicationException(ErrorCodes.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Long storeId = order.getStore().getId();
        OrderStatus newStatus = order.getStatus();

        log.info("주문 상태가 변경되었습니다. 변경 요청 시각: {}, 가게 ID: {}, 주문 ID: {}, 변경된 상태: {}"
                , statusChangedAt, storeId, orderId, newStatus);

    }
}
