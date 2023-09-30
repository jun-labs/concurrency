package project.lock.rdblock.test.member.integrationtest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.lock.rdblock.common.codeandmessage.member.MemberErrorCodeAndMessage.MEMBER_NOT_FOUND;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.lock.rdblock.common.exception.DomainException;
import project.lock.rdblock.core.domain.member.entity.Member;
import project.lock.rdblock.core.web.member.application.MemberSearchUseCase;
import project.lock.rdblock.core.web.member.exception.MemberNotFoundException;
import project.lock.rdblock.test.IntegrationTestBase;

@DisplayName("[IntegrationTest] 회원 상세조회 통합 테스트")
class MemberSearchIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MemberSearchUseCase memberSearchUseCase;

    @Test
    @DisplayName("회원이 존재하면 PK로 회원을 조회할 수 있다.")
    void member_search_test() {
        Member findMember = memberSearchUseCase.findById(1L)
            .orElseThrow(MemberNotFoundException::new);

        assertNotNull(findMember);
    }

    @Test
    @DisplayName("회원이 존재하지 않으면 MemberNotFoundException이 발생한다.")
    void invalid_member_search_test() {
        Long invalidMemberId = Long.MAX_VALUE;

        assertThatThrownBy(
            () -> memberSearchUseCase.findById(invalidMemberId)
                .orElseThrow(MemberNotFoundException::new)
        )
            .isExactlyInstanceOf(MemberNotFoundException.class)
            .isInstanceOf(DomainException.class)
            .hasMessage(MEMBER_NOT_FOUND.getKrErrorMessage());
    }
}
