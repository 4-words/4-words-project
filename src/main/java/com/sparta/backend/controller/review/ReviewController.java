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

    @GetMapping("/{orderId}/reviews")
    public List<ResponseDto> getReviewsByOrderId(@PathVariable Long orderId,@PathVariable Long reviewId) {
        return reviewService.getReviewsByOrderId(orderId);
    }

    @GetMapping("/starRating")
    public List<ResponseDto> getReviewsByStar(@RequestParam Integer min, @RequestParam Integer max) {
        return reviewService.getReviewsByStar(starRating);

    }
}