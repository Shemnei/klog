package klog.marker

class Markers {

    companion object {
        @JvmStatic
        private val markers: MutableMap<String, Marker> = mutableMapOf()

        @JvmStatic
        fun get(key: String): Marker {
            return markers.computeIfAbsent(key, { Marker(key) })
        }
    }
}