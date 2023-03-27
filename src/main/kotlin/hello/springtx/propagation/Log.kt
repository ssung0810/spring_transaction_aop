package hello.springtx.propagation

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Log(
    @Id @GeneratedValue
    val id: Long = 0L,
    val message: String
)
