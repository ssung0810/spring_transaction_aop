package hello.springtx.propagation.v2

import hello.springtx.logInfo
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UserRepositoryTx {
    @Transactional
    fun userSave() {

    }
}