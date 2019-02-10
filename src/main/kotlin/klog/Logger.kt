package klog

import klog.filter.LogFilter
import klog.sink.LogSink

interface Logger {

    val id: String

    fun log(level: LogLevel, msg: String, vararg objs: Any)
    fun log(level: LogLevel, lazyMsg: () -> String, vararg objs: Any)

    fun trace(level: LogLevel, msg: String, vararg objs: Any)
    fun trace(level: LogLevel, lazyMsg: () -> String, vararg objs: Any)

    fun debug(msg: String, vararg objs: Any) = log(LogLevel.DEBUG, msg, *objs)
    fun debug(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.DEBUG, lazyMsg, *objs)

    fun verbose(msg: String, vararg objs: Any) = log(LogLevel.VERBOSE, msg, *objs)
    fun verbose(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.VERBOSE, lazyMsg, *objs)

    fun info(msg: String, vararg objs: Any) = log(LogLevel.INFO, msg, *objs)
    fun info(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.INFO, lazyMsg, *objs)

    fun warn(msg: String, vararg objs: Any) = log(LogLevel.WARN, msg, *objs)
    fun warn(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.WARN, lazyMsg, *objs)

    fun error(msg: String, vararg objs: Any) = log(LogLevel.ERROR, msg, *objs)
    fun error(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.ERROR, lazyMsg, *objs)

    fun add(sink: LogSink): Boolean
    fun remove(sink: LogSink): Boolean

    fun add(filter: LogFilter): Boolean
    fun remove(filter: LogFilter): Boolean

    operator fun plusAssign(sink: LogSink) {
        add(sink)
    }

    operator fun minusAssign(sink: LogSink) {
        add(sink)
    }

    operator fun plusAssign(filter: LogFilter) {
        add(filter)
    }

    operator fun minusAssign(filter: LogFilter) {
        add(filter)
    }

    operator fun LogSink.unaryPlus() = add(this)
    operator fun LogSink.unaryMinus() = remove(this)

    operator fun LogFilter.unaryPlus() = add(this)
    operator fun LogFilter.unaryMinus() = remove(this)
}

fun Any.log(level: LogLevel, logger: Logger = Klog, vararg objs: Any) = logger.log(level, this.toString(), objs)