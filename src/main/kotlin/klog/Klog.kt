package klog

import klog.format.ANSIColorDecorator
import klog.format.SimpleFormat
import klog.sink.ConsoleSink

public object Klog : Logger by logger("_GLOBAL_", {
    add(ConsoleSink().apply { defaultFormat = ANSIColorDecorator(SimpleFormat()) })
})