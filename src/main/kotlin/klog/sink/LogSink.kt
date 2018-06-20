package klog.sink

import klog.LogLevel
import klog.LogRecord
import klog.filter.LogFilter
import klog.format.LogFormat
import klog.format.SimpleFormat

interface LogSink {
    val filter: MutableSet<LogFilter>

    fun accept(log: LogRecord) {
        if (filter.firstOrNull { !it.test(log) } == null) {
            processLog(log)
        }
    }

    fun processLog(log: LogRecord)
}

abstract class FormattedLogSink : LogSink {
    override val filter: MutableSet<LogFilter> = mutableSetOf()

    var defaultFormat: LogFormat = SimpleFormat()
    val formats: MutableMap<LogLevel, LogFormat> = mutableMapOf()

    fun formatRecord(log: LogRecord): String {
        val format = formats.getOrDefault(log.level, defaultFormat)
        return format.format(log)
    }
}