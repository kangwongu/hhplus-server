package kr.hhplus.be.server.order.adapter.out.persistence;

import kr.hhplus.be.server.order.adapter.out.persistence.entity.OrderEntity;
import kr.hhplus.be.server.order.adapter.out.persistence.repository.OrderRepository;
import kr.hhplus.be.server.order.application.port.out.UpdateOrderStatusPort;
import kr.hhplus.be.server.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderStatusPersistenceAdapter implements UpdateOrderStatusPort {
    private final OrderRepository orderRepository;

    @Override
    public void updateStatus(Long orderId, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 "+orderId+"가 존재하지 않는다"));
        orderEntity.updateStatus(orderStatus);

        orderRepository.save(orderEntity);
    }
}
