package com.sparta.backend.common;

import jakarta.validation.constraints.NotNull;

public enum ErrorCodes {

    INVALID_EMAIL("존재하지 않는 이메일 입니다.", 1001L),
    INVALID_PASSWORD("비밀번호가 유효하지 않습니다.", 1002L),
    USER_NOT_FOUND("유저가 존재하지 않습니다.", 1003L),
    FILE_UPLOAD_FAILED("파일 업로드에 실패하였습니다.", 2001L),

    TOKEN_NULL_EXCEPTION("토큰이 존재하지 않습니다.", 3001L),
    TOKEN_EXPIRED("토큰이 만료되었습니다.", 3002L),

    BAD_REQUEST("BAD_REQUEST", 9404L),
    BAD_REQUEST_JSON_PARSE_ERROR("[BAD_REQUEST] JSON_PARSE_ERROR - 올바른 JSON 형식이 아님", 9405L),
    // NPE
    NULL_POINT_EXCEPTION("NPE", 9998L),
    // Runtime Exception
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 9999L),
    STORE_CLOSED("영업 시간이 아닙니다.", 4001L),
    MIN_ORDER_PRICE_NOT_MET("최소 주문 금액을 충족하지 않습니다.", 4002L),
    STORE_NOT_FOUND("가게가 존재하지 않습니다.", 4003L),
    STORE_NOT_OWNER("가게의 사장이 아닙니다.", 4004L),
    MENU_NOT_FOUND("해당 메뉴를 찾을 수 없습니다.", 5001L);

    public final @NotNull String message;
    public final @NotNull Long code;

    ErrorCodes(@NotNull String message, @NotNull Long code) {
        this.message = message;
        this.code = code;
    }
}
