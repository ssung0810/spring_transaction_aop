package hello.springtx.apply

import hello.springtx.logInfo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@SpringBootTest
class InternalCallV1Test {

    @Autowired
    lateinit var callService: CallService

    @TestConfiguration
    class InternalCallV1TestConfig {
        @Bean
        fun callService(): CallService {
            return CallService()
        }
    }

    @Test
    fun printProxy() {
        logInfo("callService class=${callService.javaClass}")
    }

    @Test
    fun internalCall() {
        callService.internal()
    }

    @Test
    fun externalCall() {
        callService.external()
    }

    open class CallService {
        open fun external() {
            logInfo("call external")
            printTxInfo()
            internal()
        }

        @Transactional
        open fun internal() {
            logInfo("call internal")
            printTxInfo()
        }

        private fun printTxInfo() {
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logInfo("tx active=$txActive")
        }
    }
}