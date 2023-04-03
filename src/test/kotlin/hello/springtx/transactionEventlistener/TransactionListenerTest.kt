package hello.springtx.transactionEventlistener

import hello.springtx.logInfo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class TransactionListenerTest {
    @Autowired
    lateinit var transactionListener: TransactionListener
    @Autowired
    lateinit var eventClass: EventClass

    @TestConfiguration
    class TestConfig {
        @Bean
        fun transactionListener(): TransactionListener {
            return TransactionListener(eventClass())
        }

        @Bean
        fun eventClass(): EventClass {
            return EventClass()
        }
    }

    @Test
    fun test1() {
        transactionListener.testStart(false)
    }

    open class TransactionListener(
        private val eventClass: EventClass
    ) {
        @Transactional
        open fun testStart(isException: Boolean) {
            logInfo("트랜잭션 시작")

            eventClass.eventStart()
            if(isException) throw IllegalStateException("예외발생")

            logInfo("트랜잭션 종료")
        }
    }

    open class EventClass {
        @EventListener
        fun eventStart() {
            logInfo("event 발생")
        }
    }
}
