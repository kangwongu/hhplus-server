package kr.hhplus.be.server.product.controller.response;

import kr.hhplus.be.server.product.domain.ProductEntity;
import kr.hhplus.be.server.product.domain.ProductStatus;

import java.time.LocalDateTime;

public record ProductDetailResponse(
        Long id,
        String name,
        Long price,
        ProductStatus status,
        Integer stock,
        String description,
        LocalDateTime createDate,
        LocalDateTime updateDate
) {
    public static ProductDetailResponse from(ProductEntity p) {
        return new ProductDetailResponse(
                p.getId(), p.getName(), p.getPrice(), p.getStatus(), p.getStock(),
                p.getDescription(), p.getCreateDate(), p.getUpdateDate()
        );
    }
}
