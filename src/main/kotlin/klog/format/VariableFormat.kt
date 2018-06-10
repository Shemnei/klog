package klog.format

import klog.LogRecord


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
    }

    override fun format(logRecord: LogRecord): String {
        return variables.joinToString("") { it.toString(logRecord) }
    }
}

class VariableFormatBuilder {
    private val variables: MutableList<FormatVariable> = mutableListOf()

    operator fun String.unaryPlus() {
        variables.add(FormatVariable(FormatConstant.STRING, this))
    }

    operator fun FormatVariable.unaryPlus() {
        variables.add(this)
    }

    fun now(format: String) {
        variables.add(FormatVariable(FormatConstant.NOW_DATE_TIME, format))
    }

    fun utc(format: String) {
        variables.add(FormatVariable(FormatConstant.NOW_UTC, format))
    }

    fun created(format: String) {
        variables.add(FormatVariable(FormatConstant.LR_DATE_TIME, format))
    }

    fun createdUTC(format: String) {
        variables.add(FormatVariable(FormatConstant.LR_UTC, format))
    }

    fun level(format: String) {
        variables.add(FormatVariable(FormatConstant.LOG_LEVEL, format))
    }

    fun id(format: String) {
        variables.add(FormatVariable(FormatConstant.LOGGER_ID, format))
    }

    fun message(format: String) {
        variables.add(FormatVariable(FormatConstant.MESSAGE, format))
    }

    fun throwable(format: String) {
        variables.add(FormatVariable(FormatConstant.THROWABLE, format))
    }

    fun marker(format: String) {
        variables.add(FormatVariable(FormatConstant.MARKER, format))
    }

    fun string(str: String) {
        variables.add(FormatVariable(FormatConstant.STRING, str))
    }

    fun file(format: String) {
        variables.add(FormatVariable(FormatConstant.CALLER_FILE, format))
    }

    fun klass(format: String) {
        variables.add(FormatVariable(FormatConstant.CALLER_CLASS, format))
    }

    fun method(format: String) {
        variables.add(FormatVariable(FormatConstant.CALLER_METHOD, format))
    }

    fun line(format: String) {
        variables.add(FormatVariable(FormatConstant.CALLER_LINE, format))
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