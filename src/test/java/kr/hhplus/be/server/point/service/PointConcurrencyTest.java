package kr.hhplus.be.server.point.service;

import kr.hhplus.be.server.point.domain.PointEntity;
import kr.hhplus.be.server.point.repository.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PointConcurrencyTest {
    @Autowired PointService sut;
    @Autowired PointRepository pointRepository;

    @Test
    @DisplayName("동시 충전이 발생해도 총 충전액만큼 잔액이 증가한다")
    void rechargeConcurrency() throws InterruptedException {
        // given
        long userSeq = 1L;
        PointEntity existing = PointEntity.of(userSeq);
        existing.increase(1_000L); // 초기 잔액 1,000
        pointRepository.save(existing);

        int requestCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(requestCount);
        Long toChargeAmount = 100L;

        // when
        for (int i = 1; i <= requestCount; i++) {
            executorService.submit(() -> {
                try {
                    sut.recharge(existing.getUserSeq(), toChargeAmount);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        PointEntity result = pointRepository.findByUserSeq(existing.getUserSeq()).get();
        assertThat(result.getBalance()).isEqualTo(11_000L);
    }
}
