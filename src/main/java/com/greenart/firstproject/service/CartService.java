package com.greenart.firstproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.CouponInfoEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.entity.PaymentInfoEntity;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.CartInfoRepository;
import com.greenart.firstproject.repository.CouponInfoRefository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.cart.CartInfoVO;
import com.greenart.firstproject.vo.cart.CartPlusMinusVO;
import com.greenart.firstproject.vo.cart.DiscountVO;
import com.greenart.firstproject.vo.cart.OrderResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartInfoRepository cartRepo;
    private final OptionInfoRepository optionRepo;
    private final UserRepository userRepo;
    private final CouponInfoRefository couponRepo;

    @Transactional(readOnly = true)
    public List<CartInfoVO> getCartInfo(Long userSeq) {
        return cartRepo.findByUserSeqWithFetch(userSeq).stream().map(CartInfoVO::new).toList();
    }

    @Transactional
    public void cartAdd(Long userSeq, CartPlusMinusVO data) {
        UserEntity user = userRepo.findById(userSeq).orElseThrow();
        Optional<CartInfoEntity> existInCart = cartRepo.findByUserAndOptionSeq(user, data.getOptionSeq());
        if(existInCart.isEmpty()) {
            CartInfoEntity newCartInfo = CartInfoEntity.builder()
                .quantity(data.getQuantity())
                .user(user)
                .option(optionRepo.findById(data.getOptionSeq()).orElseThrow(() -> new IllegalArgumentException("해당 옵션이 존재하지 않습니다.")))
                .build();
            cartRepo.save(newCartInfo);
            return;
        }
        existInCart.get().addQuantity(data.getQuantity());
        // cartRepo.save(existInCart.get());
    }

    
    /**
     * 유저 엔티티와 옵션 seq번호를 이용해서 장바구니 속에 담은 요소를 삭제하는 서비스
     * @param loginUser 로그인한 세션 유저
     * @param optionSeq 장바구니 속 삭제할 옵션의 seq번호
     * @return 삭제되었다면 true 안되었다면 false
     */
    public void cartDelete(Long userSeq, Long optionSeq) {
        UserEntity loginUser = userRepo.findById(userSeq).orElseThrow();
        cartRepo.delete(cartRepo.findByUserAndOptionSeq(loginUser, optionSeq).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니 정보입니다.")));
    }

    @Transactional
    public void cartSetQuantity(Long userSeq, CartPlusMinusVO data) {
        cartRepo.findByUserSeqAndOptionSeq(userSeq, data.getOptionSeq()).ifPresent(c -> {
                c.setQuantity(data.getQuantity());
            } 
        );
    }

    @Transactional
    public OrderResult order(Long userSeq, DiscountVO discount) {
        UserEntity user = userRepo.findById(userSeq).orElseThrow();
        List<CartInfoEntity> cartInfo = cartRepo.findByUser(user);
        int finalPrice = 0;
        int originalPrice = 0;
        for(CartInfoEntity cart : cartInfo) {
            originalPrice += cart.getQuantity() * cart.getOption().getPrice();
        }
        finalPrice = originalPrice;
        if(discount.couponSeq() != null) {
            CouponInfoEntity coupon = couponRepo.findById(discount.couponSeq()).orElseThrow();
            finalPrice *= (1 - coupon.getDiscountRate());
        }
        if(discount.point() != null) {
            finalPrice -= discount.point();
        }

        return null;
    }
    
}
