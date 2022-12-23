const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

data class XY(val x: Int, val y: Int) {
    companion object {
        fun toXY(input: List<String>): List<XY> {
            return input.map {
                val s = it.split(',')
                XY(s[0].toInt(), s[1].toInt())
            }
        }
    }
}

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val rocks: List<List<XY>> = input.readLines().map{ XY.toXY(it.split(" -> "))}

    val points = rocks.flatten().toSet().plus(Cave.SOURCEXY)
    val pX = points.map{ it.x }
    val pY = points.map{ it.y }
    val min = XY(pX.min() - pY.max(), pY.min())
    val max = XY(pX.max() + pY.max(), pY.max()+2)

    val cave = Cave(min, max)
    rocks.forEach { cave.add(it) }
    cave.smooth()
    cave.sim()

    cave.print()
    println()
    println(cave.numSand)
}
