package hello.springtx.propagation.v2

import hello.springtx.logInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServiceTx(
    private val userRepositoryTx: UserRepositoryTx,
    private val logRepositoryTx: LogRepositoryTx
) {
    @Transactional
    fun dataSave() {
        logInfo("로그 시작")

        logInfo("userSave 로그 시작")
        userRepositoryTx.userSave()
        logInfo("userSave 로그 종료")

        logInfo("logSave 로그 시작")
        try {
            logRepositoryTx.logSave(true)
        } catch (e: RuntimeException) {
            logInfo("logSave 예외 발생")
        }
        logInfo("logSave 로그 종료")

        logInfo("로그 종료")
    }
}