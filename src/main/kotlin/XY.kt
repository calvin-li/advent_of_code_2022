data class XY (val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        return other is XY && x == other.x && y == other.y
    }

    override fun hashCode() = super.hashCode()
}