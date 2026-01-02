package kr.hhplus.be.server.order.application.port.in;

import kr.hhplus.be.server.order.domain.OrderResult;

/**
 * 주문 생성 유즈케이스
 */
public interface CreateOrderUseCase {
    OrderResult createSingleOrder(Long userId, Long productId, int quantity);
}
