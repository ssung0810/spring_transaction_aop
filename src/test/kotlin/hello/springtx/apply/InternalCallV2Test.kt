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
class InternalCallV2Test {

    @Autowired
    lateinit var callService: CallService

    @TestConfiguration
    class InternalCallV1TestConfig {
        @Bean
        fun callService(): CallService {
            return CallService()
        }

        @Bean
        fun internalService(): InternalService {
            return InternalService()
        }
    }

    @Test
    fun printProxy() {
        logInfo("callService class=${callService.javaClass}")
    }

    @Test
    fun externalCallV2() {
        callService.external()
    }

    class CallService {
        @Autowired
        lateinit var internalService: InternalService

        fun external() {
            logInfo("call external")
            printTxInfo()
            internalService.internal()
        }

        private fun printTxInfo() {
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logInfo("tx active=$txActive")
        }
    }

    open class InternalService {
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