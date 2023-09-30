package project.lock.rdblock.core.web.follow.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.lock.rdblock.core.domain.follow.entity.Follow;
import project.lock.rdblock.core.web.follow.application.FollowUseCase;
import project.lock.rdblock.core.web.out.LockUseCase;

@Component
@RequiredArgsConstructor
public class FollowFacade {

    private final LockUseCase lockUseCase;
    private final FollowUseCase followUseCase;

    @Transactional
    public void follow(
        Long sourceId,
        Long targetId
    ) {
        try {
            lockUseCase.getLock(targetId.toString());
            followUseCase.follow(sourceId, targetId);
        } finally {
            lockUseCase.releaseLock(targetId.toString());
        }
    }

    public Follow findFollowBy(
        Long sourceId,
        Long targetId
    ) {
        return followUseCase.findFollow(sourceId, targetId);
    }
}
