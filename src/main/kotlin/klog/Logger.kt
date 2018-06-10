package klog

import klog.filter.LogFilter
import klog.marker.Marker
import klog.sink.LogSink

class Logger internal constructor(
        private val id: String,
        val logSinks: MutableSet<LogSink> = mutableSetOf(),
        val logFilter: MutableSet<LogFilter> = mutableSetOf()
) {
    fun log(level: LogLevel, msg: String, thr: Throwable? = null, marker: Marker? = null) {
        val record = LogRecord(level, id, msg, thr, marker)
        if (logFilter.firstOrNull { !it.test(record) } == null) {
            logSinks.forEach { it.accept(record) }
        }
    }

    fun verbose(msg: String, marker: Marker? = null, thr: Throwable? = null) {
        log(LogLevel.VERBOSE, msg, thr, marker)
    }

    fun debug(msg: String, marker: Marker? = null, thr: Throwable? = null) {
        log(LogLevel.DEBUG, msg, thr, marker)
    }

    fun info(msg: String, marker: Marker? = null, thr: Throwable? = null) {
        log(LogLevel.INFO, msg, thr, marker)
    }

    fun warn(msg: String, marker: Marker? = null, thr: Throwable? = null) {
        log(LogLevel.WARN, msg, thr, marker)
    }

    fun error(msg: String, thr: Throwable? = null, marker: Marker? = null) {
        log(LogLevel.ERROR, msg, thr, marker)
    }

    fun fatal(msg: String, thr: Throwable? = null, marker: Marker? = null) {
        log(LogLevel.FATAL, msg, thr, marker)
    }
}