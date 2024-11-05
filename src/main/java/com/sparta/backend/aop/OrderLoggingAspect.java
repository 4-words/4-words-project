package com.sparta.backend.aop;
import com.sparta.backend.domain.order.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class OrderLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(OrderLoggingAspect.class);

    @AfterReturning(pointcut = "execution" +
            "(* com.sparta.backend.service.order.OrderService.createOrder(..))", returning = "order")
    public void logNewOrder(JoinPoint joinPoint, Order order) {
        Long storeId = order.getStore().getId();
        Long orderId = order.getId();
        LocalDateTime orderTime = LocalDateTime.now();
        logger.info("새로운 주문이 생성되었습니다. 주문 요청 시각: {}, 가게 ID: {}, 주문 ID: {}", orderTime, storeId, orderId);
    }

}
