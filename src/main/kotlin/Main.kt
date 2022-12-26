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
        }.filter {
            it.first <= it.second
        }.toMutableList()

        listOf(sensors.map { it.location }, sensors.map { it.beacon }).flatten().forEach { xy ->
            if (xy.y == row && 0 <= xy.x && xy.x <= B_MAX){
                intervals.add(Pair(xy.x, xy.x))
            }
        }

        intervals.sortWith{l, r ->
            if (l.first == r.first) {
                l.second.compareTo(r.second)
            } else {
                l.first.compareTo(r.first)
            }
        }

        var tX = intervals.first().second

        intervals.drop(1).forEach { i ->
            if (tX+1 < i.first){
                println("${tX+1}, $row")
                println((tX+1).toLong() * 4000000 + row)
                return
            }
            tX = max(i.second, tX)
        }
    }
}
