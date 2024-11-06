package com.sparta.backend.domain.review;

import com.sparta.backend.domain.BaseEntity;
import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.store.Store;
import com.sparta.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer starRating;

    private String content;

    private String image;
    public Review(Order order, User user, Integer starRating, String content) {
        this.order = order;
        this.user = user;
        this.starRating = starRating;
        this.content = content;
    }
}
