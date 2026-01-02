package kr.hhplus.be.server.order.domain;

import java.math.BigDecimal;

/**
 * 해당 주문을 결제하고 난 후의 결과값
 *
 * @author kangwongu-bloomingbit
 */
public record OrderResult(
        Long orderId, Long paymentId, BigDecimal paidAmount
) {
}
