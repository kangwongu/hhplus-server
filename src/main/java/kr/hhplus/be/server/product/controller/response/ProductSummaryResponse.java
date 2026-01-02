package kr.hhplus.be.server.product.controller.response;

import kr.hhplus.be.server.product.domain.ProductEntity;
import kr.hhplus.be.server.product.domain.ProductStatus;

public record ProductSummaryResponse(
        Long id,
        String name,
        Long price,
        ProductStatus status
) {
    public static ProductSummaryResponse from(ProductEntity p) {
        return new ProductSummaryResponse(p.getId(), p.getName(), p.getPrice(), p.getStatus());
    }
}
