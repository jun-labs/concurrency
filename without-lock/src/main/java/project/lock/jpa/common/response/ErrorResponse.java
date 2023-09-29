package project.lock.jpa.common.response;

import lombok.Getter;
import project.lock.jpa.common.codeandmessage.ErrorCodeAndMessage;

@Getter
public class ErrorResponse {

    private int errorCode;
    private String message;

    public ErrorResponse(ErrorCodeAndMessage codeAndMessage) {
        this.errorCode = codeAndMessage.getStatusCode();
        this.message = codeAndMessage.getKrErrorMessage();
    }

    public ErrorResponse(
        int errorCode,
        String message
    ) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
