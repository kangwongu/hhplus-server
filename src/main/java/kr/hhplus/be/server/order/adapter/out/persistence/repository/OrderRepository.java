package kr.hhplus.be.server.order.adapter.out.persistence.repository;

import kr.hhplus.be.server.order.adapter.out.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
