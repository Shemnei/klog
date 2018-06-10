package klog

import klog.marker.Marker


fun log(id: String, level: LogLevel, msg: String, thr: Throwable? = null, marker: Marker? = null) {
    val record = LogRecord(level, id, msg, thr, marker)
    if (Loggers.FILTER.firstOrNull { !it.test(record) } == null) {
        Loggers.SINKS.forEach { it.accept(record) }
    }
}

fun verbose(id: String, msg: String, marker: Marker? = null, thr: Throwable? = null) {
    log(id, LogLevel.VERBOSE, msg, thr, marker)
}

fun debug(id: String, msg: String, marker: Marker? = null, thr: Throwable? = null) {
    log(id, LogLevel.DEBUG, msg, thr, marker)
}

fun info(id: String, msg: String, marker: Marker? = null, thr: Throwable? = null) {
    log(id, LogLevel.INFO, msg, thr, marker)
}

fun warn(id: String, msg: String, marker: Marker? = null, thr: Throwable? = null) {
    log(id, LogLevel.WARN, msg, thr, marker)
}

fun error(id: String, msg: String, thr: Throwable? = null, marker: Marker? = null) {
    log(id, LogLevel.ERROR, msg, thr, marker)
}

fun fatal(id: String, msg: String, thr: Throwable? = null, marker: Marker? = null) {
    log(id, LogLevel.FATAL, msg, thr, marker)
}
