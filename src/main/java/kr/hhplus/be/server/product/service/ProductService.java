package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.product.domain.ProductEntity;
import kr.hhplus.be.server.product.domain.ProductStatus;
import kr.hhplus.be.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 목록 조회
    public Page<ProductEntity> readProducts(ProductStatus status, Pageable pageable) {
        return (status != null)
                ? productRepository.findByStatus(status, pageable)
                : productRepository.findAll(pageable);
    }

    // 상품 상세 조회
    public ProductEntity readProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));
    }

}
