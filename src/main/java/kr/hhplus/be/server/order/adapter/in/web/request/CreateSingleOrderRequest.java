package kr.hhplus.be.server.order.adapter.in.web.request;

public record CreateSingleOrderRequest(
        Long userId,
        Long productId,
        int quantity
) {}
