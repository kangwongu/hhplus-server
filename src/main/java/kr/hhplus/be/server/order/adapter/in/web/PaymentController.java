package kr.hhplus.be.server.order.adapter.in.web;

import kr.hhplus.be.server.order.application.port.in.PayUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PayUseCase useCase;

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable("id") Long id) {
        useCase.approve(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<Void> fail(@PathVariable("id") Long id) {
        useCase.fail(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        useCase.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
