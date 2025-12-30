package kr.hhplus.be.server.order.domain;

import lombok.Getter;

import java.util.Objects;

/**
 * 주문 상품 도메인
 */
@Getter
public class OrderProduct {
    private Long productId;
    private int quantity;
    private Money price;

    public OrderProduct(Long productId, int quantity, Money price) {
        if (quantity <= 0) throw new IllegalArgumentException("수량은 1 이상이다");
        Objects.requireNonNull(price);
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderProduct of(Long productId, int quantity, Money unitPrice) {
        return new OrderProduct(productId, quantity, unitPrice);
    }

}
