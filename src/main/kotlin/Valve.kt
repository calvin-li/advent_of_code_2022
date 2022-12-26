class Valve (val name: String, val flow: Int, val tunnels: List<String>, val open: Boolean = false) {
    companion object {
        val system = mutableMapOf<String, Valve>()
    }
}