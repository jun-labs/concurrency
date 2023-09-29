package project.lock.jpa.core.web.member.application;

import java.util.Optional;
import project.lock.jpa.core.domain.member.entity.Member;

public interface MemberSearchUseCase {

    Optional<Member> findById(Long sourceId);
}
