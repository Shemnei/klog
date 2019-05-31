package klog.format

import klog.LogRecord

public interface LogFormat {
    public fun format(log: LogRecord): String
}