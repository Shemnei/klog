package klog

import klog.filter.LogFilter
import klog.sink.LogSink

interface Logger {

    val id: String

    fun log(level: LogLevel, msg: String, vararg objs: Any)

    fun trace(level: LogLevel, msg: String, vararg objs: Any)

    fun debug(msg: String, vararg objs: Any) = log(LogLevel.DEBUG, msg, *objs)

    fun verbose(msg: String, vararg objs: Any) = log(LogLevel.VERBOSE, msg, *objs)

    fun info(msg: String, vararg objs: Any) = log(LogLevel.INFO, msg, *objs)

    fun warn(msg: String, vararg objs: Any) = log(LogLevel.WARN, msg, *objs)

    fun error(msg: String, vararg objs: Any) = log(LogLevel.ERROR, msg, *objs)

    fun add(sink: LogSink): Boolean
    fun remove(sink: LogSink): Boolean

    fun add(filter: LogFilter): Boolean
    fun remove(filter: LogFilter): Boolean

    operator fun LogSink.unaryPlus() = add(this)
    operator fun LogSink.unaryMinus() = remove(this)

    operator fun LogFilter.unaryPlus() = add(this)
    operator fun LogFilter.unaryMinus() = remove(this)
}

operator fun Logger.plusAssign(sink: LogSink) {
    this.add(sink)
}

operator fun Logger.minusAssign(sink: LogSink) {
    this.add(sink)
}

operator fun Logger.plusAssign(filter: LogFilter) {
    this.add(filter)
}

operator fun Logger.minusAssign(filter: LogFilter) {
    this.add(filter)
}