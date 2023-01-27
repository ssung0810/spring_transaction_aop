package hello.springtx.order

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue
    val id: Long = 0,
    var username: String = "",   // 정상, 예외, 잔고부족
    var payStatus: String = ""   // 대기, 완료
)
