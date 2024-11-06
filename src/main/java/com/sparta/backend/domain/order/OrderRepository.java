package com.sparta.backend.domain.order;

import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.store.StoreStatus;
import com.sparta.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    Optional<Order> findByUserIdAndStatus(final Long userId, final OrderStatus orderStatus);
}
