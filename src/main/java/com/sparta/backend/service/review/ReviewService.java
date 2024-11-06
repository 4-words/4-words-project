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
    public List<ResponseDto> getReviewsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Review review = reviewRepository.findById()
        List<Review> reviews = reviewRepository.findByOrderIdOrderByCreatedAtDesc(orderId);
        return reviews.stream()
                .map(ResponseDto::new)
                .collect(Collectors.toList());
    }
    //별점범위로 조회
    public List<ResponseDto> getReviewsByStar(Integer min, Integer max) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        List<Review> reviews = reviewRepository.findByStarsBetween(min, max);
        return reviews.stream()
                .map(ResponseDto::new)
                .collect(Collectors.toList());
    }
}