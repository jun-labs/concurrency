package project.lock.rdblock.core.web.follow.application;

import project.lock.rdblock.core.domain.follow.entity.Follow;
import project.lock.rdblock.core.domain.member.entity.Member;

public interface FollowUseCase {

    void follow(Long sourceId, Long targetId);

    Follow findFollow(Long sourceId, Long targetId);
}
