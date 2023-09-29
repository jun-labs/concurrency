package project.lock.jpa.test.follow.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.lock.jpa.core.domain.member.entity.Member;
import project.lock.jpa.core.domain.member.repository.MemberRepository;
import project.lock.jpa.core.web.follow.facade.FollowFacade;
import project.lock.jpa.core.web.member.exception.MemberNotFoundException;
import project.lock.jpa.test.IntegrationTestBase;

@DisplayName("[IntegrationTest] 팔로우 동기화 통합 테스트")
class FollowSyncIntegrationTest extends IntegrationTestBase {

    @Autowired
    private FollowFacade followFacade;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("동기적으로 여러 명이 순차적으로 팔로우하면 팔로우 개수만큼 팔로워가 늘어난다..")
    void follow_sync_test() {
        Long targetId = 1L;
        for (int index = 2; index <= 100; index++) {
            final Long sourceId = (long) index;
            followFacade.follow(sourceId, targetId);

        }
        Member findMember = memberRepository.findById(targetId)
            .orElseThrow(MemberNotFoundException::new);

        assertEquals(99, findMember.getFollowerCount());
    }

    // 성공하는 경우가 있을 수도 있음.
    @Test
    @DisplayName("비동기적으로 여러 명이 동시에 팔로우하면 팔로우 개수만큼 팔로워가 늘어나지 않을 수 있다.")
    void follow_async_test() throws InterruptedException {
        ExecutorService exService = Executors.newFixedThreadPool(8);
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

        latch.await();

        Member findMember = memberRepository.findById(targetId)
            .orElseThrow(MemberNotFoundException::new);

        assertNotEquals(99, findMember.getFollowerCount());
    }

    // 성공하는 경우가 있을 수도 있음.
    @Test
    @DisplayName("synchronized 키워드와 @Transactional 키워드를 동시에 사용하면 정합성이 맞지 않을 수 있다.")
    void follow_async_with_synchronized_keyword_test() throws InterruptedException {
        ExecutorService exService = Executors.newFixedThreadPool(8);
        Long targetId = 1L;
        CountDownLatch latch = new CountDownLatch(99);
        for (int index = 2; index <= 100; index++) {
            final Long sourceId = (long) index;
            exService.submit(() -> {
                try {
                    followFacade.followWithSynchronized(sourceId, targetId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Member findMember = memberRepository.findById(targetId)
            .orElseThrow(MemberNotFoundException::new);

        assertNotEquals(99, findMember.getFollowerCount());
    }
}
