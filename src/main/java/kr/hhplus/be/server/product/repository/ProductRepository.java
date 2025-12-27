package kr.hhplus.be.server.product.repository;

import kr.hhplus.be.server.product.domain.ProductEntity;
import kr.hhplus.be.server.product.domain.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByStatus(ProductStatus status, Pageable pageable);
}
