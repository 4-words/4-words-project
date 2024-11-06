package com.sparta.backend.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //List<Review> findByOrderId(Long orderId);
    List<Review> findByOrderIdOrderByCreatedAtDesc(Long orderId);
    List<Review> findByStars(Integer starRating);
    List<Review> findByStarsBetween(Integer minStar, Integer maxStar);
}