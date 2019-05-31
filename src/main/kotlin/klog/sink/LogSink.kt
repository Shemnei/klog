package klog.sink

import klog.LogLevel
import klog.LogRecord
import klog.filter.LogFilter
import klog.format.LogFormat
import klog.format.SimpleFormat

public interface LogSink {
    public val filter: MutableSet<LogFilter>

    public fun accept(log: LogRecord) {
        if (filter.all { it.test(log) }) {
            processLog(log)
        }
    }

    public fun processLog(log: LogRecord)
}

public abstract class FormattedLogSink : LogSink {
    public override val filter: MutableSet<LogFilter> = mutableSetOf()

    public var defaultFormat: LogFormat = SimpleFormat()
    public val formats: MutableMap<LogLevel, LogFormat> = mutableMapOf()

    public fun formatRecord(log: LogRecord): String {
        val format = formats.getOrDefault(log.level, defaultFormat)
        return format.format(log)
    }
}