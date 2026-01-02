package kr.hhplus.be.server.order.application.port.in;

/**
 * Pay 관련 유즈케이스
 * TODO: 이름이 너무 제너럴한지 고민
 */
public interface PayUseCase {
    void approve(Long paymentId);
    void fail(Long paymentId);
    void cancel(Long paymentId);
}
