package project.lock.rdblock.core.web.member.application;

import java.util.Optional;
import project.lock.rdblock.core.domain.member.entity.Member;

public interface MemberSearchUseCase {

    Optional<Member> findById(Long sourceId);
}
