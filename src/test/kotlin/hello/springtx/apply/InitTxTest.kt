package hello.springtx.apply

import hello.springtx.logInfo
import jakarta.annotation.PostConstruct
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@SpringBootTest
class InitTxTest {

    @Autowired
    lateinit var hello: Hello

    @Test
    fun go() {

    }

    @TestConfiguration
    class InitTxTestConfig {
        @Bean
        fun hello(): Hello {
            return Hello()
        }
    }

    open class Hello {
        @PostConstruct
        @Transactional
        open fun intiV1() {
            val isActive = TransactionSynchronizationManager.isActualTransactionActive()
            logInfo("Hello init @PostConstruct tx active=$isActive")
        }

        @EventListener(ApplicationReadyEvent::class)
        @Transactional
        open fun initV2() {
            val isActive = TransactionSynchronizationManager.isActualTransactionActive()
            logInfo("Hello init @PostConstruct tx active=$isActive")
        }
    }
}