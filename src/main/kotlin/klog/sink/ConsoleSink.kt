package klog.sink

import klog.LogLevel
import klog.LogRecord

class ConsoleSink : FormattedLogSink() {

    override fun processLog(log: LogRecord) {
        val formatted = formatRecord(log)
        if (log.level == LogLevel.ERROR) {
            System.err.println(formatted)
        } else {
            System.out.println(formatted)
        }
    }
}