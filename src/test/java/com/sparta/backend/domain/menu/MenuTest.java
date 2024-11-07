package com.sparta.backend.domain.menu;

import com.sparta.backend.controller.menu.dto.CreateRequest;
import com.sparta.backend.controller.menu.dto.UpdateRequest;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.user.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

    @Test
    @DisplayName("메뉴 상태 변경 테스트")
    void menu_update() {

        //given
        final LocalDateTime openedAt = LocalDateTime.of(
                2024,
                11,
                6,
                14,
                23,
                24,
                4);

        final LocalDateTime closedAt = LocalDateTime.of(
                2024,
                11,
                6,
                20,
                23,
                24,
                4);

        final User user = User.of(
                "테스트 이메일",
                "테스트 패스워드",
                "테스트 유저",
                "테스트 주소",
                "테스트 이미지",
                "owner");

        final Store store = Store.of(user,
                "가게 이름",
                "KOREA",
                "가게 사진",
                "가게 소개",
                "가게 주소",
                openedAt,
                closedAt,
                100000);

        final CreateRequest createRequest = new CreateRequest("족발", 10000);
        final UpdateRequest updateRequest = new UpdateRequest("피자", 20000);

        final Menu menu = new Menu(store, createRequest, "메뉴 이미지");

        // when
        menu.updated(updateRequest.getName(), updateRequest.getPrice());

        // then
        assertThat(menu.getName()).isEqualTo("피자");
        assertThat(menu.getPrice()).isEqualTo(20000);
    }

    @Test
    @DisplayName("메뉴 삭제 테스트")
    void menu_delete() {
        //given
        final LocalDateTime openedAt = LocalDateTime.of(
                2024,
                11,
                6,
                14,
                23,
                24,
                4);

        final LocalDateTime closedAt = LocalDateTime.of(
                2024,
                11,
                6,
                20,
                23,
                24,
                4);

        final User user = User.of(
                "테스트 이메일",
                "테스트 패스워드",
                "테스트 유저",
                "테스트 주소",
                "테스트 이미지",
                "owner");

        final Store store = Store.of(user,
                "가게 이름",
                "KOREA",
                "가게 사진",
                "가게 소개",
                "가게 주소",
                openedAt,
                closedAt,
                100000);

        final CreateRequest createRequest = new CreateRequest("족발", 10000);

        final Menu menu = new Menu(store, createRequest, "메뉴 이미지");

        // when
        menu.delete(MenuStatus.INACTIVE);

        // then
        assertThat(menu.getStatus()).isEqualTo(MenuStatus.INACTIVE);
    }

    @Test
    @DisplayName("메뉴 이미지 삭제")
    void menu_deleteImage() {
        //given
        final LocalDateTime openedAt = LocalDateTime.of(
                2024,
                11,
                6,
                14,
                23,
                24,
                4);

        final LocalDateTime closedAt = LocalDateTime.of(
                2024,
                11,
                6,
                20,
                23,
                24,
                4);

        final User user = User.of(
                "테스트 이메일",
                "테스트 패스워드",
                "테스트 유저",
                "테스트 주소",
                "테스트 이미지",
                "owner");

        final Store store = Store.of(user,
                "가게 이름",
                "KOREA",
                "가게 사진",
                "가게 소개",
                "가게 주소",
                openedAt,
                closedAt,
                100000);

        final CreateRequest createRequest = new CreateRequest("족발", 10000);

        final Menu menu = new Menu(store, createRequest, "메뉴 이미지");

        menu.deleteImage();

        assertThat(menu.getImage()).isEqualTo(null);
    }
}