package klog.sink

import klog.LogLevel
import klog.LogRecord
import java.io.OutputStream
import java.nio.charset.Charset

open class StreamSink(
        val mainStream: OutputStream,
        val errorStream: OutputStream = mainStream
) : FormattedLogSink() {

    override fun processLog(log: LogRecord) {
        val formatted = formatRecord(log)
        if (log.level == LogLevel.ERROR) {
            errorStream.write("$formatted\r\n".toByteArray(Charset.forName("UTF8")))
        } else {
            mainStream.write("$formatted\r\n".toByteArray(Charset.forName("UTF8")))
        }
    }
}