package kr.hhplus.be.server.order.application.port.out;

import kr.hhplus.be.server.order.domain.Payment;

public interface InsertPaymentPort {
    Long insertPayment(Payment payment);
}
