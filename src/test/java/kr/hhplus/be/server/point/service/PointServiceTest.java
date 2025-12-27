package kr.hhplus.be.server.point.service;

import kr.hhplus.be.server.point.domain.PointEntity;
import kr.hhplus.be.server.point.repository.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    PointRepository pointRepository;

    @InjectMocks
    PointService sut;

    @Test
    @DisplayName("포인트가 없을 때 충전하면 신규 엔티티를 생성하고 잔액을 반환한다")
    void rechargeCreatesWhenAbsent() {
        // given
        long userSeq = 1L;
        long amount = 5_000L;
        when(pointRepository.findWithLockByUserSeq(userSeq)).thenReturn(Optional.empty());
        when(pointRepository.save(any(PointEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        long balance = sut.recharge(userSeq, amount);

        // then
        assertThat(balance).isEqualTo(5_000L);
        verify(pointRepository).findWithLockByUserSeq(userSeq);
        verify(pointRepository).save(any(PointEntity.class));
    }

    @Test
    @DisplayName("기존 포인트가 있을 때 충전하면 잔액이 누적된다")
    void rechargeUpdatesWhenPresent() {
        // given
        long userSeq = 2L;
        long amount = 3_000L;
        PointEntity existing = PointEntity.of(userSeq);
        existing.increase(1_000L); // 초기 잔액 1,000
        when(pointRepository.findWithLockByUserSeq(userSeq)).thenReturn(Optional.of(existing));

        // when
        long balance = sut.recharge(userSeq, amount);

        // then
        assertThat(balance).isEqualTo(4_000L);
        verify(pointRepository).findWithLockByUserSeq(userSeq);
        verify(pointRepository, never()).save(any(PointEntity.class));
    }

    @Test
    @DisplayName("충전 금액이 0 이하이면 예외를 반환한다.")
    void rechargeInvalidAmountThrows() {
        // given
        long userSeq = 3L;

        // when & then
        assertThatThrownBy(() -> sut.recharge(userSeq, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("충전 금액은 0보다 커야 합니다.");
        assertThatThrownBy(() -> sut.recharge(userSeq, -100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("충전 금액은 0보다 커야 합니다.");

        verify(pointRepository, never()).findWithLockByUserSeq(anyLong());
        verify(pointRepository, never()).save(any());
    }

    @Test
    @DisplayName("포인트 잔액 조회 - 존재하면 잔액을 반환한다")
    void getBalancePresent() {
        // given
        long userSeq = 4L;
        PointEntity point = PointEntity.of(userSeq);
        point.increase(7_000L);
        when(pointRepository.findByUserSeq(userSeq)).thenReturn(Optional.of(point));

        // when
        long balance = sut.getBalance(userSeq);

        // then
        assertThat(balance).isEqualTo(7_000L);
        verify(pointRepository).findByUserSeq(userSeq);
    }

    @Test
    @DisplayName("포인트 잔액 조회 - 없으면 0을 반환한다")
    void getBalanceAbsentReturnsZero() {
        // given
        long userSeq = 5L;
        when(pointRepository.findByUserSeq(userSeq)).thenReturn(Optional.empty());

        // when
        long balance = sut.getBalance(userSeq);

        // then
        assertThat(balance).isZero();
        verify(pointRepository).findByUserSeq(userSeq);
    }

}