const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    input.readLines().forEach { line ->
        val name = line.substring(6, 8)
        val flow = line.substringAfter('=').substringBefore(';').toInt()
        val tunnels = line.substringAfter("valves").split(", ")
        Valve.system[name] = Valve(name, flow, tunnels)
    }

    println(Valve.system)
}
