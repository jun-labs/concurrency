package project.lock.rdblock.test.follow.integrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.lock.rdblock.common.codeandmessage.member.MemberErrorCodeAndMessage.FOLLOW_HISTORY_NOT_FOUND;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.lock.rdblock.core.domain.follow.entity.Follow;
import project.lock.rdblock.core.domain.member.entity.Member;
import project.lock.rdblock.core.domain.member.repository.MemberRepository;
import project.lock.rdblock.core.web.follow.exception.FollowHistoryNotFoundException;
import project.lock.rdblock.core.web.follow.facade.FollowFacade;
import project.lock.rdblock.test.IntegrationTestBase;

@DisplayName("[IntegrationTest] 팔로우 비즈니스 로직 통합 테스트")
class FollowIntegrationTest extends IntegrationTestBase {

    @Autowired
    private FollowFacade followFacade;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("팔로우 기록이 없다면 Source가 Target을 팔로우 한다.")
    void follow_test() {
        Member kim = memberRepository.save(new Member("Kim"));
        Member jang = memberRepository.save(new Member("Jang"));

        followFacade.follow(kim.getId(), jang.getId());

        Follow findFollow = followFacade.findFollowBy(kim.getId(), jang.getId());

        assertNotNull(findFollow);
    }

    @Test
    @DisplayName("팔로우 기록이 있다면 Source가 Target을 언팔로우 한다.")
    void unfollow_test() {
        Member kim = memberRepository.save(new Member("Kim"));
        Member jang = memberRepository.save(new Member("Jang"));

        followFacade.follow(kim.getId(), jang.getId());
        followFacade.follow(kim.getId(), jang.getId());

        assertThatThrownBy(() -> followFacade.findFollowBy(kim.getId(), jang.getId()))
            .isExactlyInstanceOf(FollowHistoryNotFoundException.class)
            .isInstanceOf(RuntimeException.class)
            .hasMessage(FOLLOW_HISTORY_NOT_FOUND.getKrErrorMessage());
    }
}
