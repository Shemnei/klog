package klog.sink

import klog.LogLevel
import klog.LogRecord
import klog.filter.LogFilter
import klog.format.LogFormat

interface LogSink {
    val filter: MutableSet<LogFilter>

    fun accept(logRecord: LogRecord) {
        if (filter.firstOrNull { !it.test(logRecord) } == null) {
            processLog(logRecord)
        }
    }

    fun processLog(logRecord: LogRecord)
}

interface FormattedLogSink : LogSink {
    val defaultFormat: LogFormat
    val formats: MutableMap<LogLevel, LogFormat>

    fun formatRecord(logRecord: LogRecord): String {
        val format = formats.getOrDefault(logRecord.level, defaultFormat)
        return format.format(logRecord)
    }
}