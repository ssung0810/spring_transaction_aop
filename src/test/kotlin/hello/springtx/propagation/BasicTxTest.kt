package hello.springtx.propagation

import hello.springtx.logInfo
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.UnexpectedRollbackException
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import org.springframework.transaction.support.DefaultTransactionDefinition
import javax.sql.DataSource

@SpringBootTest
class BasicTxTest {
    @Autowired
    lateinit var txManager: PlatformTransactionManager

    @TestConfiguration
    class Config {
        @Bean
        fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
            return DataSourceTransactionManager(dataSource)
        }
    }

    @Test
    fun commit() {
        logInfo("트랜잭션 시작")
        val status = txManager.getTransaction(DefaultTransactionDefinition())

        logInfo("트랜잭션 커밋 시작")
        txManager.commit(status)
        logInfo("트랜잭션 커밋 완료")
    }

    @Test
    fun rollback() {
        logInfo("트랜잭션 시작")
        val status = txManager.getTransaction(DefaultTransactionDefinition())

        logInfo("트랜잭션 롤백 시작")
        txManager.rollback(status)
        logInfo("트랜잭션 롤백 완료")
    }

    @Test
    fun double_commit() {
        logInfo("트랜잭션1 시작")
        val tx1 = txManager.getTransaction(DefaultTransactionDefinition())
        logInfo("트랜잭션1 커밋")
        txManager.commit(tx1)

        logInfo("트랜잭션2 시작")
        val tx2 = txManager.getTransaction(DefaultTransactionDefinition())
        logInfo("트랜잭션2 커밋")
        txManager.commit(tx2)
    }

    @Test
    fun double_commit_rollback() {
        logInfo("트랜잭션1 시작")
        val tx1 = txManager.getTransaction(DefaultTransactionDefinition())
        logInfo("트랜잭션1 커밋")
        txManager.commit(tx1)

        logInfo("트랜잭션2 시작")
        val tx2 = txManager.getTransaction(DefaultTransactionDefinition())
        logInfo("트랜잭션2 롤백")
        txManager.rollback(tx2)
    }

    @Test
    fun outer_commit() {
        logInfo("외부 트랜잭션 시작")
        val outer = txManager.getTransaction(DefaultTransactionAttribute())
        logInfo("outer.isNewTransaction()=${outer.isNewTransaction}")

        logInfo("내부 트랜잭션 시작")
        val inner = txManager.getTransaction(DefaultTransactionAttribute())
        logInfo("inner.isNewTransaction()=${inner.isNewTransaction}")
        logInfo("내부 트랜잭션 커밋")
        txManager.commit(inner)

        logInfo("외부 트랜잭션 커밋")
        txManager.commit(outer)
    }

    @Test
    fun outer_rollback() {
        logInfo("외부 트랜잭션 시작")
        val outer = txManager.getTransaction(DefaultTransactionAttribute())

        logInfo("내부 트랜잭션 시작")
        val inner = txManager.getTransaction(DefaultTransactionAttribute())
        logInfo("내부 트랜잭션 커밋")
        txManager.commit(inner)

        logInfo("외부 트랜잭션 롤백")
        txManager.rollback(outer)
    }

    @Test
    fun inner_rollback() {
        logInfo("외부 트랜잭션 시작")
        val outer = txManager.getTransaction(DefaultTransactionAttribute())

        logInfo("내부 트랜잭션 시작")
        val inner = txManager.getTransaction(DefaultTransactionAttribute())
        logInfo("내부 트랜잭션 롤백")
        txManager.rollback(inner)

        logInfo("외부 트랜잭션 커밋")
        assertThatThrownBy { txManager.commit(outer) }.isInstanceOf(UnexpectedRollbackException::class.java)
    }

    @Test
    fun inner_rollback_requires_new() {
        logInfo("외부 트랜잭션 시작")
        val outer = txManager.getTransaction(DefaultTransactionAttribute())
        logInfo("outer.isNewTransaction()=${outer.isNewTransaction}")

        logInfo("내부 트랜잭션 시작")
        val defaultTransactionAttribute = DefaultTransactionAttribute()
        defaultTransactionAttribute.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
        val inner = txManager.getTransaction(defaultTransactionAttribute)
        logInfo("inner.isNewTransaction()=${inner.isNewTransaction}")
        logInfo("내부 트랜잭션 커밋")
        txManager.commit(inner)

        logInfo("외부 트랜잭션 커밋")
        txManager.commit(outer)
    }
}