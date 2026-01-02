package kr.hhplus.be.server.order.application.port.out;

import kr.hhplus.be.server.order.domain.PaymentStatus;

public interface UpdatePaymentStatePort {
    void updateState(Long paymentId, PaymentStatus next);
}
