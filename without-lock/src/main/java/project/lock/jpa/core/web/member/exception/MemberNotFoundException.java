package project.lock.jpa.core.web.member.exception;

import static project.lock.jpa.common.codeandmessage.member.MemberErrorCodeAndMessage.MEMBER_NOT_FOUND;
import project.lock.jpa.common.exception.DomainException;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND);
    }
}
