package kr.hhplus.be.server.order.adapter.out.persistence.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.order.domain.PaymentStatus;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private Long orderSeq;

    @Column(nullable = false)
    private Long discountAmount;

    @Column(nullable = false)
    private Long paidAmount;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createDate;

    // Factory for creation from mapper
    public static PaymentEntity of(Long orderSeq, Long discountAmount, Long paidAmount, String state, LocalDateTime createDate) {
        PaymentEntity e = new PaymentEntity();
        e.orderSeq = orderSeq;
        e.discountAmount = discountAmount;
        e.paidAmount = paidAmount;
        e.state = state;
        e.createDate = createDate;
        return e;
    }

    public void updateState(PaymentStatus status) {
        this.state = status.name();
    }

}
