package kr.hhplus.be.server.order.adapter.out.persistence;

import kr.hhplus.be.server.order.adapter.out.persistence.mapper.OrderMapper;
import kr.hhplus.be.server.order.adapter.out.persistence.repository.OrderRepository;
import kr.hhplus.be.server.order.adapter.out.persistence.repository.PaymentRepository;
import kr.hhplus.be.server.order.application.port.out.GetOrderPort;
import kr.hhplus.be.server.order.application.port.out.InsertOrderPort;
import kr.hhplus.be.server.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements GetOrderPort, InsertOrderPort {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .map(o -> OrderMapper.toOrderDomain(o))
                .orElseThrow(() -> new IllegalArgumentException("해당 "+id+"가 존재하지 않습니다."));
    }

    @Override
    public Long insertOrder(Order order) {
        var entity = OrderMapper.toOrderEntity(order);
        var saved = orderRepository.save(entity);
        return saved.getId();
    }
}
