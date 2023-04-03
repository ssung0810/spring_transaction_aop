package hello.springtx.transactionEventlistener

import hello.springtx.logInfo
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EventClass {
    @EventListener
    fun eventStart() {
        logInfo("event 발생")
    }
}