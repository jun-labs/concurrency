package project.lock.rdblock.core.web.member.exception;

import static project.lock.rdblock.common.codeandmessage.member.MemberErrorCodeAndMessage.MEMBER_NOT_FOUND;
import project.lock.rdblock.common.exception.DomainException;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND);
    }
}
