package klog.sink

import klog.LogLevel
import klog.LogRecord
import klog.filter.LogFilter
import klog.format.LogFormat
import klog.format.VariableFormat
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SimpleFileSink(
        private val directory: String
) : FormattedLogSink {

    override val defaultFormat: LogFormat = VariableFormat.SIMPLE

    override val formats: MutableMap<LogLevel, LogFormat> = mutableMapOf()

    override val filer: MutableSet<LogFilter> = mutableSetOf()

    private val file: RandomAccessFile by lazy {
        Files.createDirectories(Paths.get(directory))
        val f = Paths.get(
                directory,
                "%s.log"
                        .format(
                                LocalDateTime
                                        .now()
                                        .format(
                                                DateTimeFormatter
                                                        .ofPattern("yyyyMMdd_HHmmss")
                                        )
                        )
        ).toFile()
        f.createNewFile()
        RandomAccessFile(f, "rw")
    }

    override fun processLog(logRecord: LogRecord) {
        file.write("%s\r\n".format(formatRecord(logRecord)).toByteArray())
    }
}