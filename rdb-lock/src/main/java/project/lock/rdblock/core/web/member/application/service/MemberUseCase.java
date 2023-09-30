package project.lock.rdblock.core.web.member.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.lock.rdblock.core.domain.member.entity.Member;
import project.lock.rdblock.core.domain.member.repository.MemberRepository;
import project.lock.rdblock.core.web.member.application.MemberSearchUseCase;
import project.lock.rdblock.core.web.out.LockUseCase;

@Service
@RequiredArgsConstructor
public class MemberUseCase implements MemberSearchUseCase, LockUseCase {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    @Transactional
    public void getLock(String key) {
        memberRepository.getLock(key);
    }

    @Override
    @Transactional
    public void releaseLock(String key) {
        memberRepository.releaseLock(key);
    }
}
