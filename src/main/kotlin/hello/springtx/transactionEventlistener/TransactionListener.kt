package hello.springtx.transactionEventlistener

import hello.springtx.logInfo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionListener(
    private val eventClass: EventClass
) {
    @Transactional
    fun testStart(isException: Boolean) {
        logInfo("트랜잭션 시작")

        eventClass.eventStart()
        if(isException) throw IllegalStateException("예외발생")

        logInfo("트랜잭션 종료")
    }
}
