package klog.format

import klog.LogRecord

interface LogFormat {
    fun format(log: LogRecord): String
}