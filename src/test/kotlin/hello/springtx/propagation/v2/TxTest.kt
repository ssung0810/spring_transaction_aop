package hello.springtx.propagation.v2

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class TxTest {
    @Autowired
    lateinit var service: ServiceTx

    @Test
    fun txTest() {
        service.dataSave()
    }
}