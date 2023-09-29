package project.lock.jpa.core.web.follow.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.lock.jpa.core.domain.follow.entity.Follow;
import project.lock.jpa.core.domain.follow.repository.FollowRepository;
import project.lock.jpa.core.domain.member.entity.Member;
import project.lock.jpa.core.web.follow.application.FollowUseCase;
import project.lock.jpa.core.web.follow.exception.FollowHistoryNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowingService implements FollowUseCase {

    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void follow(
        Member source,
        Member target
    ) {
        log.info("Before ----x> {}, {}", source, target);
        Optional<Follow> followHistory = findFollow(source, target);
        if (followHistory.isPresent()) {
            Follow follow = followHistory.get();
            updateUnFollow(source, target);
            follow.delete();
            return;
        }
        followRepository.save(new Follow(source.getId(), target.getId()));
        updateFollow(source, target);
        log.info("After ----x> {}, {}", source, target);
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
