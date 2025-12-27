package kr.hhplus.be.server.point.service;

import kr.hhplus.be.server.point.domain.PointEntity;
import kr.hhplus.be.server.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    // 포인트 충전
    @Transactional
    public long recharge(long userSeq, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다.");
        }

        // 우선 비관적 락을 걸어서 여러 쓰레드가 동시에 접근할 수 없게 함
        PointEntity point = pointRepository.findWithLockByUserSeq(userSeq)
                // TODO: 회원가입 시, 유저의 Point 를 생성할생각이고 이 부분은 아직 유저부분이 없어서 이렇게 일단 처리
                //  - 예외를 던지도록 해도 상관없을 듯
                .orElseGet(() -> pointRepository.save(PointEntity.of(userSeq)));

        point.increase(amount);
        return point.getBalance();
    }

    // 포인트 조회
    @Transactional(readOnly = true)
    public long getBalance(long userSeq) {
        return pointRepository.findByUserSeq(userSeq)
                .map(PointEntity::getBalance)
                .orElse(0L);
    }
}
