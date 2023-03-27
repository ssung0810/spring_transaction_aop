package hello.springtx.propagation.v2

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
class LogRepositoryTx {
    @Transactional//(propagation = Propagation.REQUIRES_NEW)
    fun logSave(exception: Boolean) {
        if (exception) {
            throw RuntimeException("예외발생")
        }
    }
}