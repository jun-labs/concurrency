package project.lock.jpa.core.domain.follow.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.lock.jpa.core.domain.follow.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query(
        value = "SELECT f "
            + "FROM Follow  f "
            + "WHERE f.targetId =:targetId AND f.sourceId =:sourceId AND f.deleted =:deleted"
    )
    Optional<Follow> findFollowById(
        @Param("sourceId") Long sourceId,
        @Param("targetId") Long targetId,
        @Param("deleted") Boolean deleted
    );
}
