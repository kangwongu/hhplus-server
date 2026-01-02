package kr.hhplus.be.server.order.adapter.out.persistence.mapper;

import kr.hhplus.be.server.order.adapter.out.persistence.entity.OrderEntity;
import kr.hhplus.be.server.order.adapter.out.persistence.entity.OrderProductEntity;
import kr.hhplus.be.server.order.domain.Money;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderProduct;
import kr.hhplus.be.server.order.domain.OrderStatus;

import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toOrderDomain(OrderEntity orderEntity) {
        return Order.toDomain(
                orderEntity.getId(),
                orderEntity.getUserId(),
                orderEntity.getOrderKey(),
                OrderStatus.valueOf(orderEntity.getOrderStatus()),
                orderEntity.getOrderProducts().stream()
                        .map(OrderMapper::toOrderProductDomain)
                        .collect(Collectors.toList()),
                orderEntity.getCreateDate().atStartOfDay()
        );
    }

    public static OrderProduct toOrderProductDomain(OrderProductEntity entity) {
        return OrderProduct.of(
                entity.getProductSeq(),
                entity.getQuantity(),
                Money.of(entity.getPrice())
        );
    }

    public static OrderEntity toOrderEntity(Order order) {
        // TODO: 총액 구하는 건 Order 로 옮겨놓자
        long total = order.getOrderProducts().stream()
                .mapToLong(op -> op.getPrice().amount().longValue() * op.getQuantity())
                .sum();
        return OrderEntity.of(
                order.getUserId(),
                order.getOrderKey(),
                order.getStatus().name(),
                total,
                order.getCreatedAt().toLocalDate()
        );
    }
}
