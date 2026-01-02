package kr.hhplus.be.server.order.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// Money 는 나름의 일급 컬렉션? 으로 관리
public record Money(
        BigDecimal amount
) {
    public Money {
        Objects.requireNonNull(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("음수로 생성할 수 없습니다.");
        }

        if (amount.scale() > 0) amount = amount.setScale(0, RoundingMode.DOWN);
    }

    public static Money of(long won) {
        return new Money(BigDecimal.valueOf(won));
    }

    public Money plus(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money minus(Money other) {
        BigDecimal r = this.amount.subtract(other.amount);
        if (r.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("총액보다 더 큰 금액을 뺄 수 없습니다.");
        }

        return new Money(r);
    }
}
