package hello.springtx

import org.slf4j.LoggerFactory


fun Any.logInfo(message: String) {
    val logger = LoggerFactory.getLogger(this::class.java)
    logger.info(message)
}

fun Any.logError(message: String? = null, e: Exception) {
    val logger = org.slf4j.LoggerFactory.getLogger(this.javaClass)
    logger.error(message ?: e.message ?: e.localizedMessage, e)
}
