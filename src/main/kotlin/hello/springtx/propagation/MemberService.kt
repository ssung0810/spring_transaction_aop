package hello.springtx.propagation

import hello.springtx.logInfo
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val logRepository: LogRepository
) {
    fun joinV1(username: String) {
        val member = Member(username = username)
        val logMessage = Log(message = username)


        logInfo("== memberRepository 호출 시작 ==")
        memberRepository.save(member)
        logInfo("== memberRepository 호출 종료 ==")

        logInfo("== logRepository 호출 시작 ==")
        try {
            logRepository.save(logMessage)
        } catch (e: RuntimeException) {
            logInfo("log 저장에 실패했습니다. logMessage=${logMessage.message}")
            logInfo("정상 흐름 반환")
        }

        logInfo("== logRepository 호출 종료 ==")
    }
}