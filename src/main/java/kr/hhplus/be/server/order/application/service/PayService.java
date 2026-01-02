package kr.hhplus.be.server.order.application.service;

import kr.hhplus.be.server.order.application.port.in.PayUseCase;
import kr.hhplus.be.server.order.application.port.out.GetPaymentPort;
import kr.hhplus.be.server.order.application.port.out.UpdateOrderStatusPort;
import kr.hhplus.be.server.order.application.port.out.UpdatePaymentStatePort;
import kr.hhplus.be.server.order.domain.OrderStatus;
import kr.hhplus.be.server.order.domain.Payment;
import kr.hhplus.be.server.order.domain.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService implements PayUseCase {
    private final GetPaymentPort loadPaymentPort;
    private final UpdatePaymentStatePort updatePaymentStatePort;
    private final UpdateOrderStatusPort updateOrderStatusPort;

    @Override
    @Transactional
    public void approve(Long paymentId) {
        Payment p = loadPaymentPort.getById(paymentId);
        if (p.getState() == PaymentStatus.PAID) return; // 멱등
        requirePending(p);
        updatePaymentStatePort.updateState(paymentId, PaymentStatus.PAID);
        updateOrderStatusPort.updateStatus(p.getOrderId(), OrderStatus.PAID);
    }

    @Override
    @Transactional
    public void fail(Long paymentId) {
        Payment p = loadPaymentPort.getById(paymentId);
        requirePending(p);
        updatePaymentStatePort.updateState(paymentId, PaymentStatus.FAILED);
    }

    @Override
    @Transactional
    public void cancel(Long paymentId) {
        Payment p = loadPaymentPort.getById(paymentId);
        requirePending(p);
        updatePaymentStatePort.updateState(paymentId, PaymentStatus.CANCELLED);
    }

    private void requirePending(Payment p) {
        if (p.getState() != PaymentStatus.PENDING) {
            throw new IllegalStateException("결제가 PENDING 상태가 아니다");
        }
    }
}
