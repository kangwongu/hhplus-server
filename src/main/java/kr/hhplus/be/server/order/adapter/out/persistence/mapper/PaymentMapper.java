package kr.hhplus.be.server.order.adapter.out.persistence.mapper;

import kr.hhplus.be.server.order.adapter.out.persistence.entity.PaymentEntity;
import kr.hhplus.be.server.order.domain.Money;
import kr.hhplus.be.server.order.domain.Payment;
import kr.hhplus.be.server.order.domain.PaymentStatus;
import java.time.LocalDateTime;

public class PaymentMapper {

    public static PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.of(
                payment.getOrderId(),
                payment.getDiscountAmount().amount().longValue(),
                payment.getPaidAmount().amount().longValue(),
                payment.getState().name(),
                payment.getCreatedAt()
        );
    }

    public static Payment toDomain(PaymentEntity entity) {
        return new Payment(
                entity.getSeq(),
                entity.getOrderSeq(),
                Money.of(entity.getDiscountAmount()),
                Money.of(entity.getPaidAmount()),
                PaymentStatus.valueOf(entity.getState()),
                entity.getCreateDate()
        );
    }
}
