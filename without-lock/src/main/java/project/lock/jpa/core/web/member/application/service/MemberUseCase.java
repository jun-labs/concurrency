package project.lock.jpa.core.web.member.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.lock.jpa.core.domain.member.entity.Member;
import project.lock.jpa.core.domain.member.repository.MemberRepository;
import project.lock.jpa.core.web.member.application.MemberSaveUseCase;
import project.lock.jpa.core.web.member.application.MemberSearchUseCase;

@Service
@RequiredArgsConstructor
public class MemberUseCase implements MemberSearchUseCase, MemberSaveUseCase {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }
}
