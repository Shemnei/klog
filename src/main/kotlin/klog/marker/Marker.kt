package klog.marker

class Marker(
        val id: String
) {
    private val parents: MutableSet<Marker> = HashSet()

    fun addParents(vararg markers: Marker): Marker {
        parents.addAll(markers)
        return this
    }

    fun isChildOf(marker: Marker): Boolean {
        return checkChild(marker, this)
    }

    private fun checkChild(checkMarker: Marker, currentMarker: Marker): Boolean {
        if (checkMarker == currentMarker) return true
        currentMarker.parents.forEach {
            return checkChild(checkMarker, it)
        }
        return false
    }
}