package com.sparta.backend.domain.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.backend.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest1 {
    @Test
    void changeStatus() {

        //given
        final LocalDateTime openedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);
        final LocalDateTime closedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);

        final User user = User.of("테스트 이메일", "테스트 패스워드", "테스트 유저", "테스트 주소"
                , "테스트 이미지", "user");

        final Store store = Store.of(user, "storeName", "korea", "test image", "test introduce", "test address",
                openedAt, closedAt, 1000);

        //when
        store.changeStatus();

        //then
        assertThat(store.getStatus()).isEqualTo(StoreStatus.INACTIVE);

    }

}