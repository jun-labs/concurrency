package project.lock.rdblock.core.web.out;

public interface LockUseCase {

    void getLock(String key);

    void releaseLock(String key);
}
