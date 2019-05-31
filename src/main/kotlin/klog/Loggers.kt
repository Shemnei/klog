package klog

import klog.util.getCaller

public class Loggers {

    public companion object {
        @JvmStatic
        private val loggers: MutableMap<String, Logger> = hashMapOf()

        @JvmStatic
        public fun get(key: String): Logger {
            return loggers.computeIfAbsent(key) { BaseLogger(key) }
        }
    }
}

public fun logger(id: String, body: Logger.() -> Unit): Logger {
    return Loggers.get(id).apply(body)
}

public fun classLogger(body: Logger.() -> Unit): Logger {
    val caller = getCaller("t")
    return Loggers.get(caller.className.split('.').last()).apply(body)
}