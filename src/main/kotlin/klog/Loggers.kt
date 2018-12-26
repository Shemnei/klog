package klog

class Loggers {

    companion object {
        @JvmStatic
        private val loggers: MutableMap<String, Logger> = hashMapOf()

        @JvmStatic
        fun get(key: String): Logger {
            return loggers.computeIfAbsent(key) { BaseLogger(key) }
        }
    }
}

fun logger(id: String, body: Logger.() -> Unit): Logger {
    return Loggers.get(id).apply(body)
}