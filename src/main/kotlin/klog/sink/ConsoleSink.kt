package klog.sink

import klog.LogLevel
import klog.LogRecord
import klog.filter.LogFilter
import klog.format.LogFormat
import klog.format.VariableFormat

class ConsoleSink : FormattedLogSink {

    override val defaultFormat: LogFormat = VariableFormat.SIMPLE

    override val formats: MutableMap<LogLevel, LogFormat> = mutableMapOf()

    override val filer: MutableSet<LogFilter> = mutableSetOf()

    override fun processLog(logRecord: LogRecord) {
        println(formatRecord(logRecord))
    }
}