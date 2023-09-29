package project.lock.jpa.common.codeandmessage.member;

import project.lock.jpa.common.codeandmessage.ErrorCodeAndMessage;

public enum MemberErrorCodeAndMessage implements ErrorCodeAndMessage {
    MEMBER_NOT_FOUND(
        404,
        "회원을 찾을 수 없습니다.",
        "Can not find member."
    ),
    FOLLOW_HISTORY_NOT_FOUND(
        404,
        "팔로우 기록을 찾을 수 없습니다.",
        "Can not find follow-history."
    );

    private final int code;
    private final String krErrorMessage;
    private final String engErrorMessage;

    MemberErrorCodeAndMessage(
        int code,
        String krErrorMessage,
        String engErrorMessage
    ) {
        this.code = code;
        this.krErrorMessage = krErrorMessage;
        this.engErrorMessage = engErrorMessage;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public int getStatusCode() {
        return code;
    }

    @Override
    public String getKrErrorMessage() {
        return krErrorMessage;
    }

    @Override
    public String getEnErrorMessage() {
        return engErrorMessage;
    }
}
