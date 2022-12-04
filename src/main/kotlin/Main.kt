import kotlin.math.min
import java.util.PriorityQueue

const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val scores = input.readLines().chunked(3).map { score(it) }

    println(scores.sum())
}

fun score(packs: List<String>): Int {
    val packsSet = packs.map { it.toSet() }


    val common: Char = packsSet[0].intersect(packsSet[1]).intersect(packsSet[2]).first()

    return if (common <= 'Z') {
        common - 'A' + 27
    }
    else{
        common - 'a' + 1
    }
}
