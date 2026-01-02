package kr.hhplus.be.server.order.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private Long id;
    private Long userId;
    private String orderKey;
    private OrderStatus status;
    private List<OrderProduct> orderProducts = new ArrayList<>();
    private LocalDateTime createdAt;

    public Order(Long id, Long userId, String orderKey, OrderStatus status, List<OrderProduct> orderProducts, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.orderKey = orderKey;
        this.status = status;
        this.orderProducts = orderProducts;
        this.createdAt = createdAt;
    }

    public static Order of(Long userId, List<OrderProduct> orderProducts) {
        if (orderProducts == null || orderProducts.isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 비어있지 않는다");
        }

        return new Order(null, userId, UUID.randomUUID().toString(), OrderStatus.CREATED, orderProducts, LocalDateTime.now());
    }

    public static Order toDomain(Long id, Long userId, String orderKey, OrderStatus status, List<OrderProduct> orderProducts, LocalDateTime createdAt) {
        return new Order(id, userId, orderKey, status, orderProducts, createdAt);
    }

    public BigDecimal getTotalPrice() {
        return orderProducts.stream()
                .map(op -> op.getPrice().amount().multiply(BigDecimal.valueOf(op.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
}
