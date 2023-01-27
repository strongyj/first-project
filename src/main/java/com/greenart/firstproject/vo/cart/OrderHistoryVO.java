package com.greenart.firstproject.vo.cart;

import java.time.LocalDateTime;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.OptionInfoEntity;
import com.greenart.firstproject.entity.OrderHistoryEntity;
import com.greenart.firstproject.entity.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "주문내역")
public class OrderHistoryVO {
    @Schema(description = "주문날짜")
    private LocalDateTime orderDt;
    @Schema(description = "카트에서 긁어온 제품정보")
    private Long productSeq;
    @Schema(description = "카트에서 긁어온 옵션이름")
    private String optionName;
    @Schema(description = "수량")
    private Integer quantity;
    @Schema(description = "가격")
    private Integer price;
    @Schema(description = "취소여부")
    private Boolean canceled;
    @Schema(description = "배달상태")
    private Integer deliveryStatus;

    private CartInfoVO cartInfoVO;
    private CartInfoEntity cEntity;
    private UserEntity userEntity;
    private OptionInfoEntity optionEntity;

    public OrderHistoryVO(OrderHistoryEntity entity) {
        this.orderDt = entity.getOrderDt();
        this.productSeq = entity.getProduct().getSeq();
        // this.optionName = cartInfoVO.getOptionName();
        this.optionName = optionEntity.getOption();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.canceled = entity.getCanceled();
        this.deliveryStatus = entity.getDeliveryStatus();
    }
}