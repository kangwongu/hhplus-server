package kr.hhplus.be.server.order.application.port.out;

import kr.hhplus.be.server.order.domain.Payment;

public interface GetPaymentPort {
    Payment getById(Long paymentId);
}
