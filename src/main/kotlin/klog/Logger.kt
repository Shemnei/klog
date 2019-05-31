package klog

import klog.filter.LogFilter
import klog.sink.LogSink

public interface Logger {

    public val id: String

    public fun log(level: LogLevel, msg: String, vararg objs: Any)
    public fun log(level: LogLevel, lazyMsg: () -> String, vararg objs: Any)

    public fun trace(level: LogLevel, msg: String, vararg objs: Any)
    public fun trace(level: LogLevel, lazyMsg: () -> String, vararg objs: Any)

    public fun debug(msg: String, vararg objs: Any) = log(LogLevel.DEBUG, msg, *objs)
    public fun debug(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.DEBUG, lazyMsg, *objs)

    public fun verbose(msg: String, vararg objs: Any) = log(LogLevel.VERBOSE, msg, *objs)
    public fun verbose(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.VERBOSE, lazyMsg, *objs)

    public fun info(msg: String, vararg objs: Any) = log(LogLevel.INFO, msg, *objs)
    public fun info(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.INFO, lazyMsg, *objs)

    public fun warn(msg: String, vararg objs: Any) = log(LogLevel.WARN, msg, *objs)
    public fun warn(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.WARN, lazyMsg, *objs)

    public fun error(msg: String, vararg objs: Any) = log(LogLevel.ERROR, msg, *objs)
    public fun error(lazyMsg: () -> String, vararg objs: Any) = log(LogLevel.ERROR, lazyMsg, *objs)

    public fun add(sink: LogSink): Boolean
    public fun remove(sink: LogSink): Boolean

    public fun add(filter: LogFilter): Boolean
    public fun remove(filter: LogFilter): Boolean

    public operator fun plusAssign(sink: LogSink) {
        add(sink)
    }

    public operator fun minusAssign(sink: LogSink) {
        add(sink)
    }

    public operator fun plusAssign(filter: LogFilter) {
        add(filter)
    }

    public operator fun minusAssign(filter: LogFilter) {
        add(filter)
    }

    public operator fun LogSink.unaryPlus() = add(this)
    public operator fun LogSink.unaryMinus() = remove(this)

    public operator fun LogFilter.unaryPlus() = add(this)
    public operator fun LogFilter.unaryMinus() = remove(this)
}

fun Any.log(level: LogLevel, logger: Logger = Klog, vararg objs: Any) = logger.log(level, this.toString(), objs)