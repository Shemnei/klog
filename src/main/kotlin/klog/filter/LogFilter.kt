package klog.filter

import klog.LogLevel
import klog.LogRecord
import klog.marker.Marker
import java.util.function.Predicate

public interface LogFilter : Predicate<LogRecord>

public enum class LogLevelFilterType {
    EXACT, GREATER, LESSER, GREATER_EQUALS, LESSER_EQUALS
}

public class LogLevelFilter
public constructor(
    private val level: LogLevel,
    private val type: LogLevelFilterType = LogLevelFilterType.EXACT
) : LogFilter {
    public override fun test(t: LogRecord): Boolean {
        return when (type) {
            LogLevelFilterType.EXACT -> t.level == level
            LogLevelFilterType.GREATER -> t.level > level
            LogLevelFilterType.LESSER -> t.level < level
            LogLevelFilterType.GREATER_EQUALS -> t.level >= level
            LogLevelFilterType.LESSER_EQUALS -> t.level <= level
        }
    }
}

public enum class MarkerFilterType {
    EXACT, CHILD, EXACT_OR_CHILD
}

public class MarkerFilter
public constructor(
    private val marker: Marker,
    private val type: MarkerFilterType = MarkerFilterType.EXACT_OR_CHILD
) : LogFilter {
    public override fun test(t: LogRecord): Boolean {
        t.marker ?: return false
        val isChild: Boolean by lazy { marker.isChildOf(t.marker) }
        return when (type) {
            MarkerFilterType.EXACT -> t.marker == marker
            MarkerFilterType.CHILD -> isChild
            MarkerFilterType.EXACT_OR_CHILD -> t.marker == marker || isChild
        }
    }
}

public enum class AttributeFilterType {
    MESSAGE, MARKER, THROWABLE, TRACE
}

public class AttributeFilter
public constructor(
    private val type: AttributeFilterType = AttributeFilterType.THROWABLE
) : LogFilter {
    public override fun test(t: LogRecord): Boolean {
        return when (type) {
            AttributeFilterType.MESSAGE -> t.message != null
            AttributeFilterType.MARKER -> t.marker != null
            AttributeFilterType.THROWABLE -> t.throwable != null
            AttributeFilterType.TRACE -> t.caller != null
        }
    }
}