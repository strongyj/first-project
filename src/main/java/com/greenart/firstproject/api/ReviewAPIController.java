package com.greenart.firstproject.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.service.ReviewService;
import com.greenart.firstproject.vo.ReviewVO;

import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewAPIController {
    private final ReviewService reviewService;

    @GetMapping("/reviewList")
    public Map<String, Object> getReview(@RequestParam @Nullable Long piSeq,ReviewVO data){
        Map<String, Object> resultMap = reviewService.getReview(piSeq, data);
        return resultMap;
    }
}
