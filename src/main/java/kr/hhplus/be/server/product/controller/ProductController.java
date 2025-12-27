package kr.hhplus.be.server.product.controller;

import kr.hhplus.be.server.product.controller.response.ProductDetailResponse;
import kr.hhplus.be.server.product.controller.response.ProductSummaryResponse;
import kr.hhplus.be.server.product.domain.ProductStatus;
import kr.hhplus.be.server.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회: /api/products?status=AVAILABLE&page=0&size=20&sort=price,asc
    @GetMapping
    public Page<ProductSummaryResponse> readProducts(
            @RequestParam(required = false) ProductStatus status,
            Pageable pageable
    ) {
        return productService.readProducts(status, pageable)
                .map(ProductSummaryResponse::from);
    }

    // 상품 상세 조회: /api/products/{id}
    @GetMapping("/{id}")
    public ProductDetailResponse readProduct(@PathVariable Long id) {
        return ProductDetailResponse.from(productService.readProduct(id));
    }
}
