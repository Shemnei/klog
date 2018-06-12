package klog

import klog.filter.LogFilter
import klog.sink.LogSink

class Loggers {

    companion object {
        @JvmStatic
        private val loggers: MutableMap<String, Logger> = hashMapOf()
        @JvmStatic
        val SINKS: MutableSet<LogSink> = mutableSetOf()
        @JvmStatic
        val FILTER: MutableSet<LogFilter> = mutableSetOf()

        @JvmStatic
        fun get(key: String): Logger {
            return loggers.computeIfAbsent(key, { Logger(key, SINKS.toMutableSet(), FILTER.toMutableSet()) })
        }
    }

}