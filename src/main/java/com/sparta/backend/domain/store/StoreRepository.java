package com.sparta.backend.domain.store;

import com.sparta.backend.domain.menu.Menu;
import com.sparta.backend.domain.menu.MenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByIdAndStatus(Long storeId, final StoreStatus status);
}
