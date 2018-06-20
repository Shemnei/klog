package klog.format

import klog.LogLevel
import klog.LogRecord
import java.time.format.DateTimeFormatter

class ColoredANSIFormat : LogFormat {

    companion object {
        private const val ANSI_START = "\u001B[%dm"
        private const val ANSI_END = "\u001B[0m"
        private const val RED = 31
        private const val YELLOW = 33
        private const val CYAN = 36
        private const val WHITE = 37
    }

    private val DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

    override fun format(log: LogRecord): String {
        val inner = "[%s] [%s/%s] - %s".format(log.created.format(DT_FORMAT), log.loggerId, log.level, log.message)
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