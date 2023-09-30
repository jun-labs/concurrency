package project.lock.rdblock.core.web.follow.exception;

import static project.lock.rdblock.common.codeandmessage.member.MemberErrorCodeAndMessage.FOLLOW_HISTORY_NOT_FOUND;
import project.lock.rdblock.common.exception.DomainException;

public class FollowHistoryNotFoundException extends DomainException {

    public FollowHistoryNotFoundException() {
        super(FOLLOW_HISTORY_NOT_FOUND);
    }
}
