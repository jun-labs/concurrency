package project.lock.jpa.common.codeandmessage;

public interface ErrorCodeAndMessage extends CodeAndMessage{

    String getKrErrorMessage();

    String getEnErrorMessage();
}
