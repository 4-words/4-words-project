package com.sparta.backend.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.user.User;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("오더 상태 변경 테스트")
    void order_updateStatus() {
        // given
        final LocalTime openedAt = LocalTime.of(14, 54, 5);
        final LocalTime closedAt = LocalTime.of(14, 54, 5);

        final User user = User.of(1L, "테스트 이메일", "테스트 패스워드", "테스트 유저", "테스트 주소"
                , "테스트 이미지", "user");

        final Store store = Store.of(user, "storeName", "korea", "test image", "test introduce", "test address",
                openedAt, closedAt, 1000);

        final CreateRequest createRequest = new CreateRequest("test name", 1000);

        final Menu menu = new Menu(store, createRequest, "test image");

        final Order order = new Order(user, store, menu, OrderType.DELIVERY, 10000, OrderStatus.WAITING);

        // when
        order.updateStatus(OrderStatus.COOKING);

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.COOKING);
    }
}
