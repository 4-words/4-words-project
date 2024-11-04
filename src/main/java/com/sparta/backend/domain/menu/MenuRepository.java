package com.sparta.backend.domain.menu;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByStoreIdAndStatus(final Long storeId, final MenuStatus status);
}
