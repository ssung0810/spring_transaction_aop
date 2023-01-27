package hello.springtx.order

import hello.springtx.logInfo
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTest(

) {
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    fun order() {
        val order = Order()
        order.username = "정상"

        orderService.order(order)

        val findOrder = orderRepository.findById(order.id).get()
        assertThat(findOrder.payStatus).isEqualTo("완료")
    }

    @Test
    fun runtimeException() {
        val order = Order()
        order.username = "예외"

        Assertions.assertThatThrownBy { orderService.order(order) }.isInstanceOf(RuntimeException::class.java)

        val findOrder = orderRepository.findById(order.id)
        assertThat(findOrder.isEmpty).isEqualTo(true)
    }

    @Test
    fun bizException() {
        val order = Order()
        order.username = "잔고부족"

        try {
            orderService.order(order)
        } catch (e: NotEnoughMoneyException) {
            logInfo("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내")
        }

        val findOrder = orderRepository.findById(order.id).get()
        assertThat(findOrder.payStatus).isEqualTo("대기")
    }
}