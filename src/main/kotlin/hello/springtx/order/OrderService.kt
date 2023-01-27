package hello.springtx.order

import hello.springtx.logInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    @Transactional
    fun order(order: Order) {
        logInfo("오더 호출")
        orderRepository.save(order)

        logInfo("결제 프로세스 진입")

        if (order.username == "예외") {
            logInfo("시스템 예외 발생")
            throw RuntimeException("시스템 예외")
        } else if (order.username == "잔고부족") {
            logInfo("잔고 부족 비즈니스 예외 발생")
            order.payStatus = "대기"
            throw NotEnoughMoneyException("잔고가 부족합니다.")
        } else {
            logInfo("정상 승인")
            order.payStatus = "완료"
        }

        logInfo("결제 프로세스 완료")
    }
}
