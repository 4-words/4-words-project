package com.sparta.backend.service.order;

import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.controller.order.dto.CreateOrderRequest;
import com.sparta.backend.controller.order.dto.CreateOrderResponse;
import com.sparta.backend.controller.order.dto.RetrieveOrderListResponse;
import com.sparta.backend.controller.order.dto.RetrieveOrderStatusResponse;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuRepository;
import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderRepository;
import com.sparta.backend.domain.order.OrderStatus;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreRepository;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.backend.common.ErrorCodes.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    public CreateOrderResponse createOrder(Long storeId, Long menuId,CreateOrderRequest request, Long id) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new ApplicationException(STORE_NOT_FOUND, HttpStatus.NOT_FOUND));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(()-> new ApplicationException(MENU_NOT_FOUND, HttpStatus.NOT_FOUND));
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(store.getOpenedAt()) || now.isAfter(store.getClosedAt())) {
            throw new ApplicationException(STORE_CLOSED, HttpStatus.FORBIDDEN);
        }
        if(request.getTotalPrice() < store.getMinOrderPrice()){
            throw new ApplicationException(MIN_ORDER_PRICE_NOT_MET, HttpStatus.BAD_REQUEST);
        }
        Order order = new Order(user,store,menu, request.getType(), request.getTotalPrice(), OrderStatus.WAITING);
        Order savedOrder = orderRepository.save(order);
        return new CreateOrderResponse(savedOrder);
    }

    public List<RetrieveOrderListResponse> retrieveOrder(Long id) {
        List<RetrieveOrderListResponse> orders = orderRepository.findByUserIdAndStatus(id,OrderStatus.COMPLETE).stream()
                .map(RetrieveOrderListResponse::new).toList();
        return orders;
    }

    public List<RetrieveOrderStatusResponse> retrieveOrderStatus(Long id) {
        List<RetrieveOrderStatusResponse> orders = orderRepository.findByUserId(id).stream()
                .map(RetrieveOrderStatusResponse::new).toList();
        return orders;
    }
}
