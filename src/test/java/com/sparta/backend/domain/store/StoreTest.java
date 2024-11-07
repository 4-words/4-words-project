package com.sparta.backend.domain.store;

import com.sparta.backend.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.sparta.backend.domain.store.Category.CHINA;


class StoreTest {

    @Test
    @DisplayName("스토어 업데이트 테스트")
    void updateStore() {
        //given
        final LocalDateTime openedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);
        final LocalDateTime closedAt = LocalDateTime.of(2024, 11, 6, 14, 54, 5);

        final User user = User.of("테스트 이메일", "테스트 패스워드", "테스트 유저", "테스트 주소"
                , "테스트 이미지", "user");

        final Store store = Store.of(
                user,
                "storeName",
                "korea",
                "test image",
                "test introduce",
                "test address",
                openedAt,
                closedAt,
                1000);

        store.update("storeName1",
                "china",
                "store.getImage()",
                "store.getIntroduce()",
                "store.getAddress()",
                openedAt,
                closedAt,
                2000);

        Assertions.assertThat(store.getName()).isEqualTo("storeName1");
        Assertions.assertThat(store.getCategory()).isEqualTo(CHINA);
        Assertions.assertThat(store.getImage()).isEqualTo("store.getImage()");
        Assertions.assertThat(store.getIntroduce()).isEqualTo("store.getIntroduce()");
        Assertions.assertThat(store.getAddress()).isEqualTo("store.getAddress()");
        Assertions.assertThat(store.getOpenedAt()).isEqualTo(openedAt);
        Assertions.assertThat(store.getClosedAt()).isEqualTo(closedAt);


    }

}