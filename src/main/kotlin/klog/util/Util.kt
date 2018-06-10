package klog.util

import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object Util

fun Throwable.stringify(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw, true)
    this.printStackTrace(pw)
    pw.close()
    return sw.toString().trimEnd('\r', '\n', ' ')
}

fun firstNonePackageClass(pckg: String = Util::class.java.packageName): StackTraceElement? {
    val stes = Thread.currentThread().stackTrace.toMutableList()
    stes.removeAt(0)
    return stes.firstOrNull { !it.className.startsWith(pckg) }
}

fun LocalDateTime.zone(fromZone: ZoneId, toZone: ZoneId): LocalDateTime {
    return LocalDateTime.ofInstant(ZonedDateTime.of(this, fromZone).toInstant(), toZone)
}