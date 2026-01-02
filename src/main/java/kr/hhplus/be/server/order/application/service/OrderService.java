package kr.hhplus.be.server.order.application.service;

import kr.hhplus.be.server.order.application.port.in.CreateOrderUseCase;
import kr.hhplus.be.server.order.application.port.out.InsertOrderPort;
import kr.hhplus.be.server.order.application.port.out.InsertPaymentPort;
import kr.hhplus.be.server.order.domain.*;
import kr.hhplus.be.server.product.domain.ProductEntity;
import kr.hhplus.be.server.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase {
    private final InsertOrderPort insertOrderPort;
    private final InsertPaymentPort insertPaymentPort;
    private final ProductService productService;

    @Override
    @Transactional
    public OrderResult createSingleOrder(Long userId, Long productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("수량은 1 이상이다");

        // 제품을 찾고
        ProductEntity product = productService.readProduct(productId);  // Product 는 레이어드 아키텍쳐 구조
        OrderProduct orderProduct = OrderProduct.of(product.getId(), quantity, Money.of(product.getPrice()));
        Order order = Order.of(userId, List.of(orderProduct));

        // 주문을 생성하고
        Long orderId = insertOrderPort.insertOrder(order);

        // 총액을 결제
        BigDecimal totalPrice = order.getTotalPrice();
        Payment payment = Payment.pendingFor(orderId, totalPrice.longValue(), 0L);
        Long paymentId = insertPaymentPort.insertPayment(payment);

        return new OrderResult(orderId, paymentId, payment.getPaidAmount().amount());
    }

}
