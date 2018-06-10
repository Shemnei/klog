package klog.format

import klog.util.firstNonePackageClass
import klog.util.stringify
import klog.util.zone
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class FormatVariable(
        private val constant: FormatConstant,
        private val format: String
) {
    fun toString(logRecord: klog.LogRecord): String {
        val ste: StackTraceElement? by lazy { firstNonePackageClass() }
        return when (constant) {
            FormatConstant.LR_DATE_TIME -> logRecord.created.format(DateTimeFormatter.ofPattern(format))
            FormatConstant.LR_UTC -> logRecord.created.zone(ZoneId.systemDefault(), ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern(format))
            FormatConstant.NOW_DATE_TIME -> LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))
            FormatConstant.NOW_UTC -> Instant.now().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern(format))
            FormatConstant.LOG_LEVEL -> format.format(logRecord.level)
            FormatConstant.LOGGER_ID -> format.format(logRecord.loggerId)
            FormatConstant.MESSAGE -> format.format(logRecord.message)
            FormatConstant.THROWABLE -> format.format(logRecord.throwable?.stringify() ?: "")
            FormatConstant.MARKER -> format.format(logRecord.marker ?: "")
            FormatConstant.STRING -> format
            FormatConstant.CALLER_FILE -> format.format(ste?.fileName ?: "")
            FormatConstant.CALLER_CLASS -> format.format(ste?.className ?: "")
            FormatConstant.CALLER_METHOD -> format.format(ste?.methodName ?: "")
            FormatConstant.CALLER_LINE -> format.format(ste?.lineNumber ?: -1)
        }
    }
}