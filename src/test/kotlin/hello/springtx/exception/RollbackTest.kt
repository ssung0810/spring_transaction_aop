package hello.springtx.exception

import hello.springtx.logInfo
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class RollbackTest {
    @Autowired
    lateinit var service: RollbackService

    @TestConfiguration
    class RollbackTestConfig {
        @Bean
        fun rollbackService(): RollbackService {
            return RollbackService()
        }
    }

    @Test
    fun runtimeException() {
        Assertions.assertThatThrownBy { service.runtimeException() }
            .isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun checkedException() {
        Assertions.assertThatThrownBy { service.checkedException() }
            .isInstanceOf(MyException::class.java)
    }

    @Test
    fun rollbackFor() {
        Assertions.assertThatThrownBy { service.rollbackFor() }
            .isInstanceOf(MyException::class.java)
    }

    open class RollbackService {
        // 런타임 예외 발생 : 롤백
        @Transactional
        open fun runtimeException() {
            logInfo("call runtimeException")
            throw RuntimeException()
        }

        // 체크 예외 발생 : 커밋
        @Transactional
        open fun checkedException() {
            logInfo("call checkedException")
            throw MyException()
        }

        // 체크 예외 rollbackFor 지정 : 롤백
        @Transactional(rollbackFor = [MyException::class])
        open fun rollbackFor() {
            logInfo("call checkedException")
            throw MyException()
        }
    }


}

class MyException : Exception()