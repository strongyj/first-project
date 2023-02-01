package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.security.LoginUserSeq;
import com.greenart.firstproject.service.PaymentService;
import com.greenart.firstproject.vo.cart.PaymentInfoVO;
import com.greenart.firstproject.vo.cart.paymentInfoReponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "주문내역")
@RestController
@RequestMapping("/api/paymentInfo")
@RequiredArgsConstructor
public class PaymentAPIController {
    private final PaymentService payService;
    @Operation(summary = "결제내역 불러오기")
    @ApiResponse(responseCode = "200", description = "결제내역 데이터")
    @ApiResponse(responseCode = "401", description = "로그인 되지 않은 유저가 접근할때")

    // 주문내역 출력
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getPaymentInfo(@LoginUserSeq Long uiSeq) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PaymentInfoVO> getpay = payService.getPaymentList(uiSeq);
        map.put("payList", getpay);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
