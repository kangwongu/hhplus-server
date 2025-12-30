package kr.hhplus.be.server.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MoneyTest {

    @Test
    @DisplayName("음수로 생성하면 거부된다")
    void createMoneyCantUnderZero() {
        // when
        // then
        assertThatThrownBy(() -> Money.of(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음수로 생성할 수 없습니다.");

    }

    @Test
    @DisplayName("금액을 더하면 합계가 된다")
    void plus() {
        // given
        Money init = Money.of(1000);

        // when
        Money result = init.plus(Money.of(3000));

        // then
        assertThat(result.amount()).isEqualTo(BigDecimal.valueOf(4000));
    }

    @Test
    @DisplayName("금액을 빼면 차액이 된다")
    void minus() {
        // given
        Money init = Money.of(1000);

        // when
        Money result = init.minus(Money.of(500));

        // then
        assertThat(result.amount()).isEqualTo(BigDecimal.valueOf(500));
    }

    @Test
    @DisplayName("금액을 빼면 음수가 되지 않는다")
    void minusC() {
        // given
        Money sut = Money.of(500);

        // when
        // then
        assertThatThrownBy(() -> sut.minus(Money.of(1000)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("총액보다 더 큰 금액을 뺄 수 없습니다.");

    }
    
}