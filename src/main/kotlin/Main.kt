import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.max
import kotlin.math.min

const val filename = "input.txt"
const val testFile = "test.txt"

data class Sensor(val location: XY, val beacon: XY)

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)
val B_MAX = input.readLine().toInt()

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val sensors = input.readLines().map { i ->
        i.split(' ').filter {
            it.startsWith("x=") || it.startsWith("y=")
        }.map { j ->
            j.trim{ it in "xy=:, " }
        }.map { it.toInt() }
    }.map { Sensor(XY(it[0], it[1]), XY(it[2], it[3])) }

    for(row in 0..B_MAX) {
        if (log2(row.toFloat()) % 1 == 0f){
            println("Done with row $row")
        }

        val intervals = sensors.mapNotNull { s ->
            val mDelta = abs(s.beacon.y - s.location.y) + abs(s.beacon.x - s.location.x)
            val yDelta = max(-1, mDelta - abs(row - s.location.y))
            if (yDelta >= 0) {
                Pair(
                    max(0, s.location.x - yDelta),
                    min(s.location.x + yDelta, B_MAX)
                )
            } else null
        }.filter { it.first <= it.second }

        val notIn = (intervals.minOf { it.first }..intervals.maxOf { it.second }).associateWith {
            '.'
        }.toMutableMap()

        intervals.forEach {
            (it.first..it.second).forEach { j ->
                notIn[j] = '#'
            }
        }

        sensors.forEach {
            if (it.location.x in notIn.keys && it.location.y == row) {
                notIn[it.location.x] = 'S'
            }
            if (it.beacon.x in notIn.keys && it.beacon.y == row) {
                notIn[it.beacon.x] = 'B'
            }
        }

        val possible = notIn.filter { it.value == '.' }
        if (possible.isNotEmpty()){
            val tX = possible.keys.first()
            println("$tX, $row")
            println(tX * 4000000 + row)
            return
        }
    }
}
