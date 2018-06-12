package klog.format

import klog.util.stringify
import klog.util.zone
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class FormatVariable(
        private val constant: FormatConstant,
        private val format: (Any?) -> String
) {
    fun toString(logRecord: klog.LogRecord): String {
        return when (constant) {
            FormatConstant.LR_DATE_TIME -> format(logRecord.created)
            FormatConstant.LR_UTC -> format(logRecord.created.zone(ZoneId.systemDefault(), ZoneId.of("UTC")))
            FormatConstant.NOW_DATE_TIME -> format(LocalDateTime.now())
            FormatConstant.NOW_UTC -> format(Instant.now().atZone(ZoneId.of("UTC")))
            FormatConstant.PROCESS_TIME -> format(Duration.between(LocalDateTime.now(), logRecord.created))
            FormatConstant.LOG_LEVEL -> format(logRecord.level)
            FormatConstant.LOGGER_ID -> format(logRecord.loggerId)
            FormatConstant.MESSAGE -> format(logRecord.message)
            FormatConstant.THROWABLE -> format(logRecord.throwable?.stringify() ?: "")
            FormatConstant.MARKER -> format(logRecord.marker ?: "")
            FormatConstant.STRING -> format(null)
            FormatConstant.CALLER_FILE -> format(logRecord.caller?.fileName)
            FormatConstant.CALLER_CLASS -> format(logRecord.caller?.className)
            FormatConstant.CALLER_METHOD -> format(logRecord.caller?.methodName)
            FormatConstant.CALLER_LINE -> format(logRecord.caller?.lineNumber)
        }
    }
}