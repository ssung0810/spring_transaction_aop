package hello.springtx.propagation

import hello.springtx.logInfo
import org.springframework.data.jpa.repository.JpaRepository

interface LogRepository : JpaRepository<Log, Long> {
//    fun save(logMessage: Log) {
//        logInfo("log 저장")
//
//        if (logMessage.message.contains("로그예외")) {
//            logInfo("log 저장시 예외 발생")
//            throw RuntimeException("예외 발생")
//        }
//    }

//    fun findByMessage(message: String): Log
}