package com.sparta.backend.controller.review;

import com.sparta.backend.common.resolver.AuthenticationUserId;
import com.sparta.backend.controller.review.dto.RequestDto;
import com.sparta.backend.controller.review.dto.ResponseDto;
import com.sparta.backend.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ResponseDto> createReview(
            @RequestBody RequestDto requestDto,
            @PathVariable Long orderId,
            @AuthenticationUserId Long id
    ) {
        ResponseDto responseDto = reviewService.createReview(requestDto, orderId, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{orderId}/reviews/{reviewId}")
    public ResponseEntity<List<ResponseDto>> getReviewsByOrderId(
            @PathVariable Long orderId,
            @PathVariable Long reviewId
    ) {
        List<ResponseDto> response = reviewService.getReviewsByOrderId(orderId, reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{orderId}/starRating/{reviewId}")
    public ResponseEntity<List<ResponseDto>> getReviewsByStar(
            @RequestParam Integer minStar,
            @RequestParam Integer maxStar,
            @PathVariable Long orderId,
            @PathVariable Long reviewId
    ) {
        List<ResponseDto> response = reviewService.getReviewsByStar(minStar, maxStar, orderId, reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}