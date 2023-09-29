package project.lock.jpa.core.web.follow.exception;

import static project.lock.jpa.common.codeandmessage.member.MemberErrorCodeAndMessage.FOLLOW_HISTORY_NOT_FOUND;
import project.lock.jpa.common.exception.DomainException;

public class FollowHistoryNotFoundException extends DomainException {

    public FollowHistoryNotFoundException() {
        super(FOLLOW_HISTORY_NOT_FOUND);
    }
}
