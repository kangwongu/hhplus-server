package kr.hhplus.be.server.point.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "point",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_point_user", columnNames = {"user_seq"})
        },
        indexes = {
                @Index(name = "idx_point_user", columnList = "user_seq")
        }
)
@Getter
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ERD의 seq

    @Column(nullable = false)
    private Long userSeq; // USER FK (간단히 Long으로 유지)

    @Column(nullable = false)
    private Long balance;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    protected PointEntity() {}

    @Builder
    public PointEntity(Long userSeq, Long balance) {
        this.userSeq = userSeq;
        this.balance = balance == null ? 0L : balance;
    }

    public static PointEntity of(Long userSeq) {
        return PointEntity.builder().userSeq(userSeq).balance(0L).build();
    }

    public void increase(long amount) { this.balance += amount; }
    public void decrease(long amount) { this.balance -= amount; }
}
