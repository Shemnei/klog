package klog.sink

import klog.LogRecord
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Paths

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