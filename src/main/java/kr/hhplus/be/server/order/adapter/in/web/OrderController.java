package kr.hhplus.be.server.order.adapter.in.web;
import kr.hhplus.be.server.order.adapter.in.web.request.CreateSingleOrderRequest;
import kr.hhplus.be.server.order.application.port.in.CreateOrderUseCase;
import kr.hhplus.be.server.order.domain.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResult> create(@RequestBody CreateSingleOrderRequest request) {
        OrderResult result = createOrderUseCase.createSingleOrder(
                request.userId(), request.productId(), request.quantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


}
