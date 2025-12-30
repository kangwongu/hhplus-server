package kr.hhplus.be.server.order.adapter.out.persistence.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.order.domain.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(name = "uk_orders_order_key", columnNames = {"order_key"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String orderKey;

    @Column(nullable = false)
    private String orderStatus;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate createDate;

    // 필요할까?
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductEntity> orderProducts = new ArrayList<>();

    public static OrderEntity of(Long userId, String orderKey, String orderStatus, Long totalPrice, LocalDate createDate) {
        OrderEntity e = new OrderEntity();
        e.userId = userId;
        e.orderKey = orderKey;
        e.orderStatus = orderStatus;
        e.totalPrice = totalPrice;
        e.createDate = createDate;
        return e;
    }

    public void updateStatus(OrderStatus status) {
        this.orderStatus = status.name();
    }

}
