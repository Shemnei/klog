package klog.format

import klog.LogLevel
import klog.LogRecord
import klog.marker.Marker
import klog.util.format
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class VariableFormat(
        private vararg val variables: FormatVariable
) : LogFormat {

    companion object {
        val SIMPLE: VariableFormat = variableFormat {
            created("yyyy-MM-dd@HH:mm:ss.SSS")
            level(" [%7s/")
            id("%s]: ")
            message("%s")
        }
        val THROWABLE: VariableFormat = variableFormat {
            created("yyyy-MM-dd@HH:mm:ss.SSS")
            level(" [%7s/")
            id("%s]: ")
            message("%s")
            throwable(" - %s")
        }
        val TRACE: VariableFormat = variableFormat {
            created("yyyy-MM-dd@HH:mm:ss.SSS")
            +"#"
            process { it.format() }
            level(" [%7s/")
            id("%s] ")
            file("(%s:")
            line("%s): ")
            message("%s")
        }
    }

    override fun format(logRecord: LogRecord): String {
        return variables.joinToString("") { it.toString(logRecord) }
    }
}

class VariableFormatBuilder {
    private val variables: MutableList<FormatVariable> = mutableListOf()

    operator fun String.unaryPlus() {
        variables.add(FormatVariable(FormatConstant.STRING, { this }))
    }

    operator fun FormatVariable.unaryPlus() {
        variables.add(this)
    }

    fun now(format: (LocalDateTime) -> String) {
        variables.add(FormatVariable(FormatConstant.NOW_DATE_TIME, format as (Any?) -> String))
    }

    fun now(pattern: String) {
        val func = { dt: LocalDateTime -> dt.format(DateTimeFormatter.ofPattern(pattern)) }
        variables.add(FormatVariable(FormatConstant.NOW_DATE_TIME, func as (Any?) -> String))
    }

    fun utc(format: (LocalDateTime) -> String) {
        variables.add(FormatVariable(FormatConstant.NOW_UTC, format as (Any?) -> String))
    }

    fun utc(pattern: String) {
        val func = { dt: LocalDateTime -> dt.format(DateTimeFormatter.ofPattern(pattern)) }
        variables.add(FormatVariable(FormatConstant.NOW_UTC, func as (Any?) -> String))
    }

    fun created(format: (LocalDateTime) -> String) {
        variables.add(FormatVariable(FormatConstant.LR_DATE_TIME, format as (Any?) -> String))
    }

    fun created(pattern: String) {
        val func = { dt: LocalDateTime -> dt.format(DateTimeFormatter.ofPattern(pattern)) }
        variables.add(FormatVariable(FormatConstant.LR_DATE_TIME, func as (Any?) -> String))
    }

    fun createdUTC(format: (LocalDateTime) -> String) {
        variables.add(FormatVariable(FormatConstant.LR_UTC, format as (Any?) -> String))
    }

    fun createdUTC(pattern: String) {
        val func = { dt: LocalDateTime -> dt.format(DateTimeFormatter.ofPattern(pattern)) }
        variables.add(FormatVariable(FormatConstant.LR_UTC, func as (Any?) -> String))
    }

    fun process(format: (Duration) -> String) {
        variables.add(FormatVariable(FormatConstant.PROCESS_TIME, format as (Any?) -> String))
    }

    fun process(format: String) {
        val func = { duration: Duration -> format.format(duration.toMillis()) }
        variables.add(FormatVariable(FormatConstant.PROCESS_TIME, func as (Any?) -> String))
    }

    fun level(format: (LogLevel) -> String) {
        variables.add(FormatVariable(FormatConstant.LOG_LEVEL, format as (Any?) -> String))
    }

    fun level(format: String) {
        val func = { level: LogLevel -> format.format(level) }
        variables.add(FormatVariable(FormatConstant.LOG_LEVEL, func as (Any?) -> String))
    }

    fun id(format: (String) -> String) {
        variables.add(FormatVariable(FormatConstant.LOGGER_ID, format as (Any?) -> String))
    }

    fun id(format: String) {
        val func = { id: String -> format.format(id) }
        variables.add(FormatVariable(FormatConstant.LOGGER_ID, func as (Any?) -> String))
    }

    fun message(format: (String?) -> String) {
        variables.add(FormatVariable(FormatConstant.MESSAGE, format as (Any?) -> String))
    }

    fun message(format: String) {
        val func = { msg: String? -> format.format(msg ?: "") }
        variables.add(FormatVariable(FormatConstant.MESSAGE, func as (Any?) -> String))
    }

    fun throwable(format: (Throwable?) -> String) {
        variables.add(FormatVariable(FormatConstant.THROWABLE, format as (Any?) -> String))
    }

    fun throwable(format: String) {
        val func = { thr: Throwable? -> format.format(thr ?: Unit) }
        variables.add(FormatVariable(FormatConstant.THROWABLE, func as (Any?) -> String))
    }

    fun marker(format: (Marker?) -> String) {
        variables.add(FormatVariable(FormatConstant.MARKER, format as (Any?) -> String))
    }

    fun marker(format: String) {
        val func = { marker: Marker? -> format.format(marker ?: Unit) }
        variables.add(FormatVariable(FormatConstant.MARKER, func as (Any?) -> String))
    }

    fun string(str: String) {
        variables.add(FormatVariable(FormatConstant.STRING, { str }))
    }

    fun file(format: (String?) -> String) {
        variables.add(FormatVariable(FormatConstant.CALLER_FILE, format as (Any?) -> String))
    }

    fun file(format: String) {
        val func = { file: String? -> format.format(file ?: "ERR_NO_TRACE") }
        variables.add(FormatVariable(FormatConstant.CALLER_FILE, func as (Any?) -> String))
    }

    fun klass(format: (String?) -> String) {
        variables.add(FormatVariable(FormatConstant.CALLER_CLASS, format as (Any?) -> String))
    }

    fun klass(format: String) {
        val func = { klass: String? -> format.format(klass ?: "ERR_NO_TRACE") }
        variables.add(FormatVariable(FormatConstant.CALLER_CLASS, func as (Any?) -> String))
    }

    fun method(format: (String?) -> String) {
        variables.add(FormatVariable(FormatConstant.CALLER_METHOD, format as (Any?) -> String))
    }

    fun method(format: String) {
        val func = { method: String? -> format.format(method ?: "ERR_NO_TRACE") }
        variables.add(FormatVariable(FormatConstant.CALLER_METHOD, func as (Any?) -> String))
    }

    fun line(format: (Int?) -> String) {
        variables.add(FormatVariable(FormatConstant.CALLER_LINE, format as (Any?) -> String))
    }

    fun line(format: String) {
        val func = { line: Int? -> format.format(line ?: "ERR_NO_TRACE") }
        variables.add(FormatVariable(FormatConstant.CALLER_LINE, func as (Any?) -> String))
    }

    fun variable(variable: FormatVariable) {
        variables.add(variable)
    }

    fun build(): VariableFormat {
        return VariableFormat(*this.variables.toTypedArray())
    }

}

fun variableFormat(init: VariableFormatBuilder.() -> Unit): VariableFormat {
    val vfb = VariableFormatBuilder()
    vfb.init()
    return vfb.build()
}