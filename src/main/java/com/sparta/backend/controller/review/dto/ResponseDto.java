package com.sparta.backend.controller.review.dto;

import com.sparta.backend.domain.review.Review;
import lombok.Getter;

@Getter
public class ResponseDto {
    private Long id;
    private String star_rating;
    private String content;
    private String image;
    private String created_at;

    public ResponseDto(Review review) {
        this.id = review.getId();
        this.star_rating = review.getStarRating().toString();
        this.content = review.getContent();
        this.image = review.getImage();
        this.created_at = review.getCreatedAt().toString();
    }
}