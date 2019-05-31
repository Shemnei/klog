package klog.format

import klog.LogLevel
import klog.LogRecord

public class ANSIColorDecorator
public constructor(
    private val format: LogFormat
) : LogFormat by format {

    public companion object {
        private const val ANSI_START = "\u001B[%dm"
        private const val ANSI_END = "\u001B[0m"
        private const val RED = 31
        private const val YELLOW = 33
        private const val CYAN = 36
        private const val WHITE = 37
    }

    public override fun format(log: LogRecord): String {
        val inner = format.format(log)
        return getColorString(log.level).format(inner)
    }

    private fun getColorString(level: LogLevel): String {
        return when (level) {
            LogLevel.ERROR -> "${ANSI_START.format(RED)}%s$ANSI_END"
            LogLevel.WARN -> "${ANSI_START.format(YELLOW)}%s$ANSI_END"
            LogLevel.INFO -> "${ANSI_START.format(CYAN)}%s$ANSI_END"
            LogLevel.VERBOSE -> "${ANSI_START.format(WHITE)}%s$ANSI_END"
            LogLevel.DEBUG -> "${ANSI_START.format(WHITE)}%s$ANSI_END"
        }
    }
}