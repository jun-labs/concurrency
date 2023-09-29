package project.lock.jpa.core.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.lock.jpa.core.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
