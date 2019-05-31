package klog.sink

import klog.LogLevel
import klog.LogRecord
import java.io.OutputStream
import java.nio.charset.Charset

public open class StreamSink
public constructor(
    private val mainStream: OutputStream,
    private val errorStream: OutputStream = mainStream
) : FormattedLogSink() {

    public override fun processLog(log: LogRecord) {
        val formatted = formatRecord(log)
        if (log.level == LogLevel.ERROR) {
            errorStream.write("$formatted\r\n".toByteArray(Charset.forName("UTF8")))
        } else {
            mainStream.write("$formatted\r\n".toByteArray(Charset.forName("UTF8")))
        }
    }
}