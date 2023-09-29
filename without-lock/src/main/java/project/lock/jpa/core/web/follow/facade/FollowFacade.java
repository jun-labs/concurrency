package project.lock.jpa.core.web.follow.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.lock.jpa.core.domain.follow.entity.Follow;
import project.lock.jpa.core.domain.member.entity.Member;
import project.lock.jpa.core.web.follow.application.FollowUseCase;
import project.lock.jpa.core.web.member.application.MemberSearchUseCase;
import project.lock.jpa.core.web.member.exception.MemberNotFoundException;

@Component
@RequiredArgsConstructor
public class FollowFacade {

    private final MemberSearchUseCase memberSearchUseCase;
    private final FollowUseCase followUseCase;

    @Transactional
    public void follow(
        Long sourceId,
        Long targetId
    ) {
        Member findSource = memberSearchUseCase.findById(sourceId)
            .orElseThrow(MemberNotFoundException::new);
        Member findTarget = memberSearchUseCase.findById(targetId)
            .orElseThrow(MemberNotFoundException::new);
        followUseCase.follow(findSource, findTarget);
    }

    public Follow findFollowBy(
        Long sourceId,
        Long targetId
    ) {
        return followUseCase.findFollow(sourceId, targetId);
    }
}
