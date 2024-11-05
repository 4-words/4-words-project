package com.sparta.backend.common;

import jakarta.validation.constraints.NotNull;

public enum ErrorCodes {

    INVALID_EMAIL("존재하지 않는 이메일 입니다.", 1001L),
    INVALID_PASSWORD("비밀번호가 유효하지 않습니다.", 1002L),
    FILE_UPLOAD_FAILED("파일 업로드에 실패하였습니다.", 2001L),

    TOKEN_NULL_EXCEPTION("토큰이 존재하지 않습니다.", 3001L),
    TOKEN_EXPIRED("토큰이 만료되었습니다.",3002L),

    STORE_NOT_OWNER("해당 가게 주인이 아닙니다.", 5002L),
    NOT_OWNER("사장만 가능할 수 있습니다", 5003L),

    BAD_REQUEST("BAD_REQUEST", 9404L),
    BAD_REQUEST_JSON_PARSE_ERROR("[BAD_REQUEST] JSON_PARSE_ERROR - 올바른 JSON 형식이 아님", 9405L),
    // NPE
    NULL_POINT_EXCEPTION("NPE", 9998L),
    // Runtime Exception
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 9999L);

    public final @NotNull String message;
    public final @NotNull Long code;

    ErrorCodes(@NotNull String message, @NotNull Long code) {
        this.message = message;
        this.code = code;
    }
}
