package klog

import klog.marker.Marker
import java.time.ZonedDateTime
import java.util.*

data class LogRecord(
        val level: LogLevel,
        val loggerId: String,
        val logMessage: String? = null,
        val lazyMessage: (() -> String)?,
        val throwable: Throwable? = null,
        val marker: Marker? = null,
        val caller: StackTraceElement? = null,
        val args: List<Any>? = null
) {
    val guid: UUID = UUID.randomUUID()
    val created: ZonedDateTime = ZonedDateTime.now()

    val message: String? by lazy { logMessage ?: lazyMessage?.invoke() }
}

