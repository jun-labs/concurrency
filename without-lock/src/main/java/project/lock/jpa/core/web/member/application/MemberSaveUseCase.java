package project.lock.jpa.core.web.member.application;

import project.lock.jpa.core.domain.member.entity.Member;

public interface MemberSaveUseCase {

    void save(Member member);
}
