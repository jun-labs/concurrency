package project.lock.jpa.core.web.follow.application;

import project.lock.jpa.core.domain.follow.entity.Follow;
import project.lock.jpa.core.domain.member.entity.Member;

public interface FollowUseCase {

    void follow(Member source, Member target);

    void followWithSynchronized(Member source, Member target);

    Follow findFollow(Long sourceId, Long targetId);
}
