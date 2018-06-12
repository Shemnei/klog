package klog.sink

import klog.LogRecord
import klog.Logger
import klog.filter.LogFilter

// TODO: 12.06.2018 maybe find a better way to relay loggers
class LoggerSink(
        val logger: Logger
) : LogSink {
    override val filter: MutableSet<LogFilter> = mutableSetOf()

    override fun processLog(logRecord: LogRecord) {
        logger.log(logRecord.level, logRecord.message!!, logRecord.throwable, logRecord.marker)
    }
}