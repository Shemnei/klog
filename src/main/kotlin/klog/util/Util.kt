package klog.util

import klog.Logger
import java.io.PrintWriter
import java.io.StringWriter
import java.time.Duration

public fun Throwable.stringify(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw, true)
    this.printStackTrace(pw)
    pw.close()
    return sw.toString().trimEnd('\r', '\n', ' ')
}

public fun getCaller(pckg: String = Logger::class.java.packageName): StackTraceElement {
    val stackTraceElements = Thread.currentThread().stackTrace.toMutableList()
    return stackTraceElements.first {
        !it.className.startsWith(pckg)
                && !it.className.startsWith("java")
                && !it.className.startsWith("kotlin")
    }
}

// HH:mm:ss.SSS
public fun Duration.format(): String {
    val millis = this.toMillis()
    val absMillis = Math.abs(millis)
    val positive = String.format(
            "%02d:%02d.%03d",
            absMillis / 60000,
            (absMillis % 60000) / 1000,
            absMillis % 1000)
    return if (millis < 0) "-$positive" else positive
}