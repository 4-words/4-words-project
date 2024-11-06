package com.sparta.backend.service.review;

import com.sparta.backend.common.ApplicationException;
import com.sparta.backend.controller.review.dto.RequestDto;
import com.sparta.backend.controller.review.dto.ResponseDto;
import com.sparta.backend.domain.order.Order;
import com.sparta.backend.domain.order.OrderRepository;
import com.sparta.backend.domain.order.OrderStatus;
import com.sparta.backend.domain.review.Review;
import com.sparta.backend.domain.review.ReviewRepository;
import com.sparta.backend.domain.user.User;
import com.sparta.backend.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.backend.common.ErrorCodes.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto createReview(RequestDto requestDto, Long orderId, Long id) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!order.getStatus().equals(OrderStatus.COMPLETE)){
            throw new ApplicationException(DELIVERY_NOT_COMPLETE, HttpStatus.BAD_REQUEST);
        }

        Review review = new Review(order,user,requestDto.getStarRating(), requestDto.getContent());
        Review savedReview = reviewRepository.saveAndFlush(review);
        ResponseDto responseDto = new ResponseDto(savedReview);
        return responseDto;
    }

    //주문별로 조회
    public List<ResponseDto> getReviewsByOrderId(Long orderId, Long reviewId) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApplicationException(REVIEW_NOT_FOUND, HttpStatus.NOT_FOUND));

        List<Review> reviews = reviewRepository.findByOrderIdOrderByCreatedAtDesc(orderId);
        return reviews.stream()
                .map(ResponseDto::new)
                .collect(Collectors.toList());
    }
    //별점범위로 조회
    public List<ResponseDto> getReviewsByStar(Integer minStar, Integer maxStar, Long orderId, Long reviewId) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApplicationException(REVIEW_NOT_FOUND, HttpStatus.NOT_FOUND));

        int min = (minStar != null) ? minStar : 1;
        int max = (maxStar != null) ? maxStar : 5;

        if (min < 1 || max > 5 || min > max) {
            throw new ApplicationException(STAR_NOT_VALID, HttpStatus.NOT_FOUND);
        }

        List<Review> reviews = reviewRepository.findByStarRatingBetween(minStar, maxStar);
        return reviews.stream()
                .map(ResponseDto::new)
                .collect(Collectors.toList());
    }
}