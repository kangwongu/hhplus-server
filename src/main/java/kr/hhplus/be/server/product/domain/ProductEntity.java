package kr.hhplus.be.server.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "product",
        indexes = {
                @Index(name = "idx_product_status", columnList = "status"),
                @Index(name = "idx_product_create_date", columnList = "create_date")
        }
)
@Getter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private ProductStatus status;

    @Column(nullable = false)
    private Integer stock;

    private String description;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    //
    protected ProductEntity() {}

    @Builder
    public ProductEntity(String name, Long price, ProductStatus status, Integer stock, String description) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.stock = stock;
        this.description = description;
    }

    public static ProductEntity of(String name, long price, ProductStatus status, int stock, String description) {
        return ProductEntity.builder()
                .name(name)
                .price(price)
                .status(status)
                .stock(stock)
                .description(description)
                .build();
    }

    public void changeStatus(ProductStatus status) { this.status = status; }
    public void changePrice(long price) { this.price = price; }
    public void changeStock(int stock) { this.stock = stock; }
    public void changeDescription(String description) { this.description = description; }
}
