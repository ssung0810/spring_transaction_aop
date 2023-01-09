package hello.springtx.apply

import hello.springtx.logInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@SpringBootTest
class TxBasicTest {

    @Autowired
    lateinit var basicService: BasicService

    @Test
    fun proxyCheck() {
        logInfo("aop class=${basicService.javaClass}")
        assertThat(AopUtils.isAopProxy(basicService)).isTrue
    }

    @Test
    fun txTest() {
        basicService.tx()
        basicService.nonTx()
    }

    @TestConfiguration
    class TxApplyBasicConfig {
        @Bean
        fun basicService(): BasicService {
            return BasicService()
        }
    }

    open class BasicService {
        @Transactional(readOnly = true)
        open fun tx() {
            logInfo("call tx")
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logInfo("tx active=${txActive}")
        }

        fun nonTx() {
            logInfo("call nonTx")
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logInfo("tx active=${txActive}")
        }
    }
}
