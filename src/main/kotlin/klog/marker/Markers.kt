package klog.marker

object Markers {
    private val markers: MutableMap<String, Marker> = mutableMapOf()

    fun get(key: String): Marker {
        return markers.computeIfAbsent(key, { Marker(key) })
    }
}