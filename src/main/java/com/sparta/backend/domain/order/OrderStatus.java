package com.sparta.backend.domain.order;

import com.sparta.backend.common.ApplicationException;

public enum OrderStatus {
    WAITING, COOKING, DELIVERING, COMPLETE

    public OrderStatus getNextStatus() {
        switch (this) {
            case WAITING:
                return COOKING;
            case COOKING:
                return DELIVERING;
            case DELIVERING:
                return COMPLETE;
            default:
                throw new ApplicationException();
        }
    }
}
