package klog.sink

import klog.LogLevel
import klog.LogRecord
import klog.filter.LogFilter
import klog.format.LogFormat
import klog.format.SimpleFormat
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SimpleFileSink(
        private val filePath: String
) : FormattedLogSink() {

    private val file: RandomAccessFile by lazy {
        val f = Paths.get(filePath).toFile()
        Files.createDirectories(Paths.get(f.parent))
        f.createNewFile()
        RandomAccessFile(f, "rw")
    }

    override fun processLog(log: LogRecord) {
        file.write("%s\r\n".format(formatRecord(log)).toByteArray())
    }
}