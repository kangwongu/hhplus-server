package kr.hhplus.be.server.order.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Payment {
    private Long id; // 식별자
    private Long orderId;
    private Money discountAmount; // 포인트/쿠폰 등 총 할인
    private Money paidAmount;     // 실제 결제 금액
    private PaymentStatus state;
    private LocalDateTime createdAt;

    public Payment(Long id, Long orderId, Money discountAmount, Money paidAmount, PaymentStatus state, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.discountAmount = discountAmount;
        this.paidAmount = paidAmount;
        this.state = state;
        this.createdAt = createdAt;
    }

    public static Payment pendingFor(Long orderId, long totalPrice, long discount) {
        long appliedDiscount = Math.min(totalPrice, Math.max(discount, 0));
        long paid = totalPrice - appliedDiscount;
        return new Payment(
                null,
                orderId,
                Money.of(appliedDiscount),
                Money.of(paid),
                PaymentStatus.PENDING,
                LocalDateTime.now()
        );
    }
}
