package klog

import klog.marker.Marker
import java.time.LocalDateTime
import java.time.ZonedDateTime

// TODO: 10.06.2018 maybe a log record id?
data class LogRecord(
        val level: LogLevel,
        val loggerId: String,
        val message: String? = null,
        val throwable: Throwable? = null,
        val marker: Marker? = null,
        val caller: StackTraceElement? = null,
        val args: List<Any>? = null
) {
    val created: ZonedDateTime = ZonedDateTime.now()
}

