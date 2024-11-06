package com.sparta.backend.domain.store;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.domain.review.QReview;
import com.sparta.backend.domain.store.dto.StoreRetrieveResponseByCategory;
import com.sparta.backend.domain.store.dto.StoreRetrieveSortResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<StoreRetrieveResponseByCategory> retrieveByCategory(final String type, final Pageable pageable) {
        final QStore store = QStore.store;

        final Category category = Category.from(type);

        final List<StoreRetrieveResponseByCategory> content = queryFactory
                .select(Projections.constructor(
                        StoreRetrieveResponseByCategory.class,
                        store.id,
                        store.name,
                        store.category.stringValue(),
                        store.image,
                        store.introduce,
                        store.address,
                        store.openedAt,
                        store.closedAt,
                        store.minOrderPrice
                ))
                .from(store)
                .where(store.category.eq(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final Optional<Long> totalOptional = Optional.ofNullable(queryFactory
                .select(store.count())
                .from(store)
                .where(store.category.eq(category))
                .fetchOne());

        final long total = totalOptional.orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<StoreRetrieveSortResponse> retrieveBySort(final String orderBy, final Pageable pageable) {
        final QStore store = QStore.store;
        final QReview review = QReview.review;

        final OrderSpecifier<?> sortOrder = getSortOrder(review);

        final List<StoreRetrieveSortResponse> content = queryFactory
                .select(Projections.constructor(
                        StoreRetrieveSortResponse.class,
                        store.id,
                        store.name,
                        store.category.stringValue(),
                        store.image,
                        store.introduce,
                        store.address,
                        store.openedAt,
                        store.closedAt,
                        store.minOrderPrice
                ))
                .from(store)
                .leftJoin(review).on(review.store.eq(store))
                .groupBy(store.id)
                .orderBy(sortOrder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final Long totalValue = queryFactory
                .select(store.count())
                .from(store)
                .leftJoin(review).on(review.store.eq(store))
                .groupBy(store.id)
                .fetchFirst();

        final long total = Optional.ofNullable(totalValue).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    private OrderSpecifier<?> getSortOrder(QReview review) {
        return review.count().desc();
    }
}