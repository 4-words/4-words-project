package com.sparta.backend.domain.store;

import com.sparta.backend.domain.store.dto.StoreRetrieveResponseByCategory;
import com.sparta.backend.domain.store.dto.StoreRetrieveSortResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreCustomRepository {

    Page<StoreRetrieveResponseByCategory> retrieveByCategory(
            final String type,
            final Pageable pageable
    );

    Page<StoreRetrieveSortResponse> retrieveBySort(
        final String orderBy,
        final Pageable pageable
    );
}
