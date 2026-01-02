package kr.hhplus.be.server.order.adapter.out.persistence;

import kr.hhplus.be.server.order.adapter.out.persistence.mapper.PaymentMapper;
import kr.hhplus.be.server.order.adapter.out.persistence.repository.PaymentRepository;
import kr.hhplus.be.server.order.application.port.out.InsertPaymentPort;
import kr.hhplus.be.server.order.application.port.out.GetPaymentPort;
import kr.hhplus.be.server.order.application.port.out.UpdatePaymentStatePort;
import kr.hhplus.be.server.order.domain.Payment;
import kr.hhplus.be.server.order.domain.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements InsertPaymentPort, GetPaymentPort, UpdatePaymentStatePort {
    private final PaymentRepository paymentRepository;

    @Override
    public Long insertPayment(Payment payment) {
        var entity = PaymentMapper.toEntity(payment);
        return paymentRepository.save(entity).getSeq();
    }

    @Override
    public Payment getById(Long paymentId) {
        var entity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 결제 "+paymentId+"가 존재하지 않는다"));
        return PaymentMapper.toDomain(entity);
    }

    @Override
    public void updateState(Long paymentId, PaymentStatus paymentStatus) {
        var entity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 결제 "+paymentId+"가 존재하지 않는다"));
        // 엔티티에 세터가 없으므로 간단히 새 상태로 저장
        entity.updateState(paymentStatus);
        paymentRepository.save(entity);
    }

}
