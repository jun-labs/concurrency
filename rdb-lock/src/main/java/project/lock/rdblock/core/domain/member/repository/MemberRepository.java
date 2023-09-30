package project.lock.rdblock.core.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.lock.rdblock.core.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT get_lock(:targetId, 1000)", nativeQuery = true)
    void getLock(@Param("targetId") String targetId);

    @Query(value = "SELECT release_lock(:targetId)", nativeQuery = true)
    void releaseLock(@Param("targetId") String targetId);
}
