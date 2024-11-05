package com.sparta.backend.domain.store;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.domain.store.dto.StoreRetrieveResponseByCategory;
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
        QStore store = QStore.store;

        Category category = Category.from(type);

        List<StoreRetrieveResponseByCategory> content = queryFactory
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

        Optional<Long> totalOptional = Optional.ofNullable(queryFactory
                .select(store.count())
                .from(store)
                .where(store.category.eq(category))
                .fetchOne());

        long total = totalOptional.orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
