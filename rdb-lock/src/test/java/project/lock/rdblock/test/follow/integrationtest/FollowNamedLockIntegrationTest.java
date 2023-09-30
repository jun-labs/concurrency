package project.lock.rdblock.test.follow.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.lock.rdblock.core.domain.member.entity.Member;
import project.lock.rdblock.core.domain.member.repository.MemberRepository;
import project.lock.rdblock.core.web.follow.facade.FollowFacade;
import project.lock.rdblock.core.web.member.exception.MemberNotFoundException;
import project.lock.rdblock.test.IntegrationTestBase;

@DisplayName("[IntegrationTest] 팔로우 Named Lock 동기화 통합 테스트")
class FollowNamedLockIntegrationTest extends IntegrationTestBase {

    @Autowired
    private FollowFacade followFacade;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Named Lock을 사용하면 비동기적으로 여러 명이 동시에 팔로우해도 정합성을 보장할 수 있다.")
    void named_lock_async_test() throws InterruptedException {
        ExecutorService exService = Executors.newFixedThreadPool(32);
        Long targetId = 1L;
        CountDownLatch latch = new CountDownLatch(99);

        for (int index = 2; index <= 100; index++) {
            final Long sourceId = (long) index;
            exService.submit(() -> {
                try {
                    followFacade.follow(sourceId, targetId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);

        Member findMember = memberRepository.findById(targetId)
            .orElseThrow(MemberNotFoundException::new);

        assertEquals(99, findMember.getFollowerCount());
    }
}
