package kr.hhplus.be.server.point.controller;

import kr.hhplus.be.server.point.controller.request.PointRechargeRequest;
import kr.hhplus.be.server.point.controller.response.PointBalanceResponse;
import kr.hhplus.be.server.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userSeq}/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping("/recharge")
    public PointBalanceResponse recharge(@PathVariable long userSeq,
                                         @RequestBody PointRechargeRequest request) {
        long balance = pointService.recharge(userSeq, request.amount());
        return new PointBalanceResponse(userSeq, balance);
    }

    @GetMapping
    public PointBalanceResponse get(@PathVariable long userSeq) {
        long balance = pointService.getBalance(userSeq);
        return new PointBalanceResponse(userSeq, balance);
    }
}
