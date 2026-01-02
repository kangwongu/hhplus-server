package kr.hhplus.be.server.order.application.port.out;

import kr.hhplus.be.server.order.domain.OrderStatus;

public interface UpdateOrderStatusPort {
    void updateStatus(Long orderId, OrderStatus next);
}
