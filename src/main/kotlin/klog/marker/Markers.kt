package klog.marker

public class Markers {

    public companion object {
        @JvmStatic
        private val markers: MutableMap<String, Marker> = mutableMapOf()

        @JvmStatic
        public fun get(key: String): Marker {
            return markers.computeIfAbsent(key) { Marker(key) }
        }
    }
}