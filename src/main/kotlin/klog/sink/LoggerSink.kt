package klog.sink

import klog.LogRecord
import klog.Logger
import klog.filter.LogFilter

// TODO: 12.06.2018 maybe find a better way to relay loggers
// // FIXME: 20.06.2018 args wont get relayed
class LoggerSink(
        val logger: Logger
) : LogSink {
    override val filter: MutableSet<LogFilter> = mutableSetOf()

    override fun processLog(log: LogRecord) {
        logger.log(log.level, log.message!!, log.throwable, log.marker)
    }
}