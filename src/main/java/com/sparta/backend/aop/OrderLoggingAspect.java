package com.sparta.backend.aop;
import com.sparta.backend.controller.order.dto.CreateOrderResponse;
import com.sparta.backend.domain.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class OrderLoggingAspect {

    @AfterReturning(pointcut = "execution(* com.sparta.backend.service.order.OrderService.createOrder(..))",
            returning = "response")
    public void logNewOrder(JoinPoint joinPoint, CreateOrderResponse response) {
        Long storeId = response.getId(); // 가게 ID가 아니라 주문 ID를 가져옴
        String storeName = response.getStoreName();
        Long orderId = response.getId();
        LocalDateTime createdAt = response.getCreatedAt();

        log.info("새로운 주문이 생성되었습니다. 주문 요청 시각: {}, 가게 ID: {}, 주문 ID: {}", createdAt, storeName, orderId);
    }
}
