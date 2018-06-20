package klog.format

import klog.LogRecord
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SimpleFormat : LogFormat {

    private val DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

    override fun format(log: LogRecord): String {
        return "[%s] [%s/%s] - %s".format(log.created.format(DT_FORMAT), log.loggerId, log.level, log.message)
    }
}