package kr.hhplus.be.server.order.adapter.out.persistence.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.order.adapter.out.persistence.entity.key.OrderProductId;
import org.springframework.data.annotation.CreatedDate;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_product")
@Getter
public class OrderProductEntity {
    @EmbeddedId
    private OrderProductId id;

    // TODO: 간접참조로?
    @MapsId("orderSeq")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_seq")
    private OrderEntity order;

    @Column(insertable = false, updatable = false)
    private Long productSeq;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Long price; // 단가

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createDate;

    // setters (저장을 위한 최소 세터)
    public void setId(OrderProductId id) { this.id = id; }
    public void setOrder(OrderEntity order) { this.order = order; }
    public void setProductSeq(Long productSeq) { this.productSeq = productSeq; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPrice(Long price) { this.price = price; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
}
