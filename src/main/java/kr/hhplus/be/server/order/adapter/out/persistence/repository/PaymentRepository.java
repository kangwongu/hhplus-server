package kr.hhplus.be.server.order.adapter.out.persistence.repository;

import kr.hhplus.be.server.order.adapter.out.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
