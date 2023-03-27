package hello.springtx.propagation

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceTest {
    @Autowired
    lateinit var memberService: MemberService
    @Autowired
    lateinit var memberRepository: MemberRepository
    @Autowired
    lateinit var logRepository: LogRepository

    @Test
    fun outerTxOff_success() {
         // given
        val username = "outerTxOff_success"

        // when
        memberService.joinV1(username)

        // then: 모든 데이터가 정상 저장된다.
//        Assertions.assertThat(memberRepository.findByUsername(username).username).isEqualTo(username)
//        Assertions.assertThat(logRepository.findByMessage(username).message).isEqualTo(username)
    }
}