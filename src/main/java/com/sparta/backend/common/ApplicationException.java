package com.sparta.backend.common;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private @NotNull
    final ErrorCodes errorCodes;
    private @NotNull
    final HttpStatus status;

    public ApplicationException(@NotNull ErrorCodes errorCodes, @NotNull HttpStatus status) {
        this.errorCodes = errorCodes;
        this.status = status;
    }

    public @NotNull ErrorCodes getErrorCodes() {
        return errorCodes;
    }

    public @NotNull HttpStatus getStatus() {
        return status;
    }
}
