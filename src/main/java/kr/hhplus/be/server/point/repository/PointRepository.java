package kr.hhplus.be.server.point.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.point.domain.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
    Optional<PointEntity> findByUserSeq(Long userSeq);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PointEntity> findWithLockByUserSeq(Long userSeq);
}
