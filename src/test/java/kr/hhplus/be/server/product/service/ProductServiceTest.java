package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.product.domain.ProductEntity;
import kr.hhplus.be.server.product.domain.ProductStatus;
import kr.hhplus.be.server.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    ProductRepository productRepository;

    // Mock 객체들로 테스트하고자 하는 주체인 ProductService 주입
    @InjectMocks
    ProductService sut;

    @Test
    @DisplayName("제품 상태로 검색하면 조건에 맞는 제품 목록이 조회된다")
    void readProductsByProductStatus() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<ProductEntity> items = List.of(
                ProductEntity.of("아이폰", 1_200_000L, ProductStatus.AVAILABLE, 50, "최신 아이폰"),
                ProductEntity.of("갤럭시", 1_000_000L, ProductStatus.AVAILABLE, 30, "플래그십")
        );
        Page<ProductEntity> page = new PageImpl<>(items, pageable, items.size());

        // 제품 2개 stub
        when(productRepository.findByStatus(ProductStatus.AVAILABLE, pageable))
                .thenReturn(page);

        // when
        Page<ProductEntity> result = sut.readProducts(ProductStatus.AVAILABLE, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent())
                .extracting("name", "price", "status", "stock", "description")
                .containsExactly(
                        tuple("아이폰", 1_200_000L, ProductStatus.AVAILABLE, 50, "최신 아이폰"),
                        tuple("갤럭시", 1_000_000L, ProductStatus.AVAILABLE, 30, "플래그십")
                );
    }

    @Test
    @DisplayName("제품 상태 없이 검색하면 모든 제품이 조회된다")
    void readProductsNoProductStatus() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<ProductEntity> items = List.of(
                ProductEntity.of("아이폰", 1_200_000L, ProductStatus.AVAILABLE, 50, "최신 아이폰"),
                ProductEntity.of("갤럭시", 1_000_000L, ProductStatus.AVAILABLE, 30, "플래그십"),
                ProductEntity.of("맥북", 2_000_000L, ProductStatus.OUT_OF_STOCK, 30, "최신 맥북"),
                ProductEntity.of("아이맥", 1_500_000L, ProductStatus.AVAILABLE, 30, "고철 아이맥"),
                ProductEntity.of("맥스튜디오", 1_300_000L, ProductStatus.UNAVAILABLE, 30, "그냥 맥스튜디오")
        );
        Page<ProductEntity> page = new PageImpl<>(items, pageable, items.size());

        // 제품 2개 stub
        when(productRepository.findAll(pageable))
                .thenReturn(page);

        // when
        Page<ProductEntity> result = sut.readProducts(null, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(5);
        assertThat(result.getContent())
                .extracting("name", "price", "status", "stock", "description")
                .containsExactly(
                        tuple("아이폰", 1_200_000L, ProductStatus.AVAILABLE, 50, "최신 아이폰"),
                        tuple("갤럭시", 1_000_000L, ProductStatus.AVAILABLE, 30, "플래그십"),
                        tuple("맥북", 2_000_000L, ProductStatus.OUT_OF_STOCK, 30, "최신 맥북"),
                        tuple("아이맥", 1_500_000L, ProductStatus.AVAILABLE, 30, "고철 아이맥"),
                        tuple("맥스튜디오", 1_300_000L, ProductStatus.UNAVAILABLE, 30, "그냥 맥스튜디오")
                );
    }

    @Test
    @DisplayName("ID로 상세 조회하면 해당 제품이 조회된다")
    void readProductById() {
        // given
        ProductEntity product = ProductEntity.of("맥북", 2_000_000L, ProductStatus.AVAILABLE, 10, "M칩 탑재");
        // 제품 1개 stub
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when
        ProductEntity found = sut.readProduct(1L);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("맥북");
        assertThat(found.getStock()).isEqualTo(10);
        assertThat(found.getStatus()).isEqualTo(ProductStatus.AVAILABLE);
    }

    @Test
    @DisplayName("존재하지 않는 제품 ID로 상세 조회하면 조회가 거부된다")
    void readProductByNotExistId() {
        // when
        // then
        assertThatThrownBy(() -> sut.readProduct(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품을 찾을 수 없습니다. id=1");

    }
}