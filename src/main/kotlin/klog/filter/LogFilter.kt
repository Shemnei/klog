package klog.filter

import klog.LogLevel
import klog.LogRecord
import klog.marker.Marker
import java.util.function.Predicate

interface LogFilter : Predicate<LogRecord>

enum class LogLevelFilterType {
    EXACT, GREATER, LESSER, GREATER_EQUALS, LESSER_EQUALS
}

class LogLevelFilter(
        private val level: LogLevel,
        private val type: LogLevelFilterType = LogLevelFilterType.EXACT
) : LogFilter {
    override fun test(t: LogRecord): Boolean {
        return when (type) {
            LogLevelFilterType.EXACT -> t.level == level
            LogLevelFilterType.GREATER -> t.level > level
            LogLevelFilterType.LESSER -> t.level < level
            LogLevelFilterType.GREATER_EQUALS -> t.level >= level
            LogLevelFilterType.LESSER_EQUALS -> t.level <= level
        }
    }
}

enum class MarkerFilterType {
    EXACT, CHILD, EXACT_OR_CHILD
}

class MarkerFilter(
        private val marker: Marker,
        private val type: MarkerFilterType = MarkerFilterType.EXACT_OR_CHILD
) : LogFilter {
    override fun test(t: LogRecord): Boolean {
        t.marker ?: return false
        val isChild: Boolean by lazy { marker.isChildOf(t.marker) }
        return when (type) {
            MarkerFilterType.EXACT -> t.marker == marker
            MarkerFilterType.CHILD -> isChild
            MarkerFilterType.EXACT_OR_CHILD -> t.marker == marker || isChild
        }
    }
}