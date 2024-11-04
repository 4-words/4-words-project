package com.sparta.backend.service.order;

import com.sparta.backend.controller.order.dto.CreateOrderRequest;
import com.sparta.backend.controller.order.dto.CreateOrderResponse;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderRepository;
import com.sparta.backend.domain.order.OrderStatus;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import com.sparta.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;


    // 1. 주문 생성하기
    public CreateOrderResponse createOrder(CreateOrderRequest request, User user) {
        // 가게 Id로 가게 조회
        Store store = storeRepository.findById(request.getStoreId()).orElse(null);
        // 메뉴 Id로 메뉴 조회
        Menu menu = menuRepository.findById(request.getMenuId()).orElse(null);
        //
        Order order = new Order(user,store,menu, request.getType(), request.getTotalPrice(), OrderStatus.WAITING);

        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponse(savedOrder);
    }








    // 2. 주문 내역 조회



    // 3. 주문 상태 조회



    // 4. 주문 취소
}
