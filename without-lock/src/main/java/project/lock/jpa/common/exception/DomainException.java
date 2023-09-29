package project.lock.jpa.common.exception;

import lombok.Getter;
import project.lock.jpa.common.codeandmessage.ErrorCodeAndMessage;

@Getter
public class DomainException extends RuntimeException {

    private final ErrorCodeAndMessage codeAndMessage;

    public DomainException(ErrorCodeAndMessage codeAndMessage) {
        super(codeAndMessage.getKrErrorMessage());
        this.codeAndMessage = codeAndMessage;
    }
}
