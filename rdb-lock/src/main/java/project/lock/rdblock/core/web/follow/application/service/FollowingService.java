package project.lock.rdblock.core.web.follow.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.lock.rdblock.core.domain.follow.entity.Follow;
import project.lock.rdblock.core.domain.follow.repository.FollowRepository;
import project.lock.rdblock.core.domain.member.entity.Member;
import project.lock.rdblock.core.domain.member.repository.MemberRepository;
import project.lock.rdblock.core.web.follow.application.FollowUseCase;
import project.lock.rdblock.core.web.follow.exception.FollowHistoryNotFoundException;
import project.lock.rdblock.core.web.member.exception.MemberNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowingService implements FollowUseCase {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void follow(
        Long sourceId,
        Long targetId
    ) {
        Member findSource = memberRepository.findById(sourceId)
            .orElseThrow(MemberNotFoundException::new);
        Member findTarget = memberRepository.findById(targetId)
            .orElseThrow(MemberNotFoundException::new);
        log.info("Before ----x> {}, {}", findSource, findTarget);

        Optional<Follow> followHistory = findFollow(findSource, findTarget);
        if (followHistory.isPresent()) {
            Follow follow = followHistory.get();
            updateUnFollow(findSource, findTarget);
            follow.delete();
            return;
        }
        followRepository.save(new Follow(findSource.getId(), findTarget.getId()));
        updateFollow(findSource, findTarget);
        log.info("After ----x> {}, {}", findSource, findTarget);
    }

    @Override
    public Follow findFollow(Long sourceId, Long targetId) {
        return followRepository.findFollowById(sourceId, targetId, Boolean.FALSE)
            .orElseThrow(FollowHistoryNotFoundException::new);
    }

    private void updateUnFollow(
        Member source,
        Member target
    ) {
        source.decreaseFollowingCount();
        target.decreaseFollowerCount();
    }

    private void updateFollow(
        Member source,
        Member target
    ) {
        source.increaseFollowingCount();
        target.increaseFollowerCount();
    }

    private Optional<Follow> findFollow(Member source, Member target) {
        return followRepository.findFollowById(
            source.getId(),
            target.getId(),
            Boolean.FALSE
        );
    }
}
