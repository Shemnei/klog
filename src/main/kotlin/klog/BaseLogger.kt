package klog

import klog.filter.LogFilter
import klog.marker.Marker
import klog.sink.LogSink
import klog.util.getCaller

public class BaseLogger
internal constructor(
    public override val id: String,
    private val sinks: MutableSet<LogSink> = mutableSetOf(),
    private val filters: MutableSet<LogFilter> = mutableSetOf()
) : Logger {

    public override fun log(level: LogLevel, msg: String, vararg objs: Any) {
        val record = createRecord(level, msg, null, false, objs)
        if (filters.firstOrNull { !it.test(record) } == null) {
            sinks.forEach { it.accept(record) }
        }
    }

    public override fun log(level: LogLevel, lazyMsg: () -> String, vararg objs: Any) {
        val record = createRecord(level, null, lazyMsg, false, objs)
        if (filters.firstOrNull { !it.test(record) } == null) {
            sinks.forEach { it.accept(record) }
        }
    }

    public override fun trace(level: LogLevel, msg: String, vararg objs: Any) {
        val record = createRecord(level, msg, null, true, objs)
        if (filters.firstOrNull { !it.test(record) } == null) {
            sinks.forEach { it.accept(record) }
        }
    }

    public override fun trace(level: LogLevel, lazyMsg: () -> String, vararg objs: Any) {
        val record = createRecord(level, null, lazyMsg, false, objs)
        if (filters.firstOrNull { !it.test(record) } == null) {
            sinks.forEach { it.accept(record) }
        }
    }

    public override fun add(sink: LogSink): Boolean = sinks.add(sink)
    public override fun remove(sink: LogSink): Boolean = sinks.remove(sink)

    public override fun add(filter: LogFilter): Boolean = filters.add(filter)
    public override fun remove(filter: LogFilter): Boolean = filters.remove(filter)

    private fun createRecord(level: LogLevel, msg: String?, lazyMsg: (() -> String)?, trace: Boolean, objs: Array<out Any>): LogRecord {
        val marker = objs.firstOrNull { it is Marker } as Marker?
        val throwable = objs.firstOrNull { it is Throwable } as Throwable?
        return LogRecord(level, this.id, msg, lazyMsg, throwable, marker, if (trace) getCaller() else null)
    }
}

