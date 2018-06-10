package klog

import klog.filter.LogFilter
import klog.sink.LogSink

object Loggers {

    private val loggers: MutableMap<String, Logger> = hashMapOf()

    val SINKS: MutableSet<LogSink> = mutableSetOf()
    val FILTER: MutableSet<LogFilter> = mutableSetOf()

    fun getLogger(key: String): Logger {
        return loggers.computeIfAbsent(key, { Logger(key, SINKS.toMutableSet(), FILTER.toMutableSet()) })
    }
}