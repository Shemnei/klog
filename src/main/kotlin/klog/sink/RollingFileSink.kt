package klog.sink

import klog.LogLevel
import klog.LogRecord
import klog.filter.LogFilter
import klog.format.LogFormat
import klog.format.VariableFormat
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface RollingStrategy {
    fun shouldRoll(logRecord: LogRecord): Boolean
    fun doRoll(logRecord: LogRecord)
}

class RecordCountRollingStrategy(
        private val maxRecordCount: Long = 10_000
) : RollingStrategy {

    private var recordCount: Long = 0

    override fun shouldRoll(logRecord: LogRecord): Boolean {
        recordCount++
        return recordCount >= maxRecordCount
    }

    override fun doRoll(logRecord: LogRecord) {
        recordCount = 0
    }
}

class DurationRollingStrategy(
        private val duration: Duration = Duration.ofHours(1)
) : RollingStrategy {

    private val intervalSeconds: Long = duration.seconds
    private var start: Long = Instant.now().epochSecond

    override fun shouldRoll(logRecord: LogRecord): Boolean {
        return Instant.now().epochSecond - start >= intervalSeconds
    }

    override fun doRoll(logRecord: LogRecord) {
        start = Instant.now().epochSecond
    }
}

// FIXME: 11.06.2018 not very optimal, double formatting different formats etc...
class SizeRollingStrategy(
        private val format: LogFormat,
        private val sizeBytes: Long = 1000 * 1000 * 4 // 4mb
) : RollingStrategy {

    private var bytes: Long = 0

    override fun shouldRoll(logRecord: LogRecord): Boolean {
        bytes += format.format(logRecord).toByteArray().size
        return bytes >= sizeBytes
    }

    override fun doRoll(logRecord: LogRecord) {
        bytes = 0
    }
}

class RollingFileSink(
        private val directory: String,
        private val strategy: RollingStrategy
) : FormattedLogSink {

    override val defaultFormat: LogFormat = VariableFormat.SIMPLE
    override val formats: MutableMap<LogLevel, LogFormat> = mutableMapOf()
    override val filter: MutableSet<LogFilter> = mutableSetOf()
    private lateinit var file: RandomAccessFile

    private val fileName: String
        get() = "%s.log".format(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss.SSS")))

    init {
        Files.createDirectories(Paths.get(directory))
    }

    private fun createNewFile(): RandomAccessFile {
        val f = Paths.get(directory, fileName).toFile()
        f.createNewFile()
        return RandomAccessFile(f, "rw")
    }

    private fun doRoll() {
        file.close()
        file = createNewFile()
    }

    override fun processLog(logRecord: LogRecord) {
        if (!this::file.isInitialized)
            file = createNewFile()
        if (strategy.shouldRoll(logRecord)) {
            doRoll()
            strategy.doRoll(logRecord)
        }
        file.write("%s\r\n".format(formatRecord(logRecord)).toByteArray())
    }
}