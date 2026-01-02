package kr.hhplus.be.server.order.application.port.out;

import kr.hhplus.be.server.order.domain.Order;

public interface InsertOrderPort {
    Long insertOrder(Order order);
}
