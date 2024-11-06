package com.sparta.backend.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByOrderIdOrderByCreatedAtDesc(Long orderId);

    List<Review> findByStarRatingBetween(Integer minStar, Integer maxStar);
}