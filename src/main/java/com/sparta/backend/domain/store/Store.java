package com.sparta.backend.domain.store;

import com.sparta.backend.domain.BaseEntity;
import com.sparta.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String image;

    private String introduce;

    private String address;

    private LocalTime openedAt;

    private LocalTime closedAt;

    private Integer minOrderPrice;

    private Double starRating;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    public Store(final User user,
                 final String name,
                 final Category category,
                 final String image,
                 final String introduce,
                 final String address,
                 final LocalTime openedAt,
                 final LocalTime closedAt,
                 final Integer minOrderPrice,
                 final StoreStatus status
    ) {
        this.user = user;
        this.name = name;
        this.category = category;
        this.image = image;
        this.introduce = introduce;
        this.address = address;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.minOrderPrice = minOrderPrice;
        this.status = status;
    }

    public static Store of(
            final User user,
            final String name,
            final String category,
            final String image,
            final String introduce,
            final String address,
            final LocalTime openedAt,
            final LocalTime closedAt,
            final Integer minOrderPrice
    ) {
        return new Store(user, name, Category.from(category), image, introduce, address, openedAt, closedAt,
                minOrderPrice, StoreStatus.ACTIVE);
    }

    public boolean isOwner(final Long loginId) {
        return this.user.getId().equals(loginId);
    }

    public void update(
            final String name,
            final String category,
            final String image,
            final String introduce,
            final String address,
            final LocalTime openedAt,
            final LocalTime closedAt,
            final Integer minOrderPrice
    ) {
        this.name = name;
        this.category = Category.from(category);
        this.image = image;
        this.introduce = introduce;
        this.address = address;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.minOrderPrice = minOrderPrice;
    }

    public void changeStatus(){
        this.status = StoreStatus.INACTIVE;
    }
}
