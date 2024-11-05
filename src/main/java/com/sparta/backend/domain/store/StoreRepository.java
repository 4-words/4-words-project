package com.sparta.backend.domain.store;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByIdAndStatus(final Long storeId, final StoreStatus status);
}
