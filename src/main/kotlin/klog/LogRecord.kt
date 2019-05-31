package klog

import klog.marker.Marker
import java.time.ZonedDateTime
import java.util.*

public data class LogRecord
public constructor(
    public val level: LogLevel,
    public val loggerId: String,
    public val logMessage: String? = null,
    public val lazyMessage: (() -> String)?,
    public val throwable: Throwable? = null,
    public val marker: Marker? = null,
    public val caller: StackTraceElement? = null,
    public val args: List<Any>? = null
) {
    public val guid: UUID = UUID.randomUUID()
    public val created: ZonedDateTime = ZonedDateTime.now()

    public val message: String by lazy { logMessage ?: lazyMessage?.invoke() ?: "" }
}

