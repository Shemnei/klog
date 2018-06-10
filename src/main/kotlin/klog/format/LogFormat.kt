package klog.format

import klog.LogRecord

interface LogFormat {
    fun format(logRecord: LogRecord): String
}