const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val containers: List<Boolean> = input.readLines().map { contain(it) }
    println(containers.count { it })
}

fun contain(assigns: String): Boolean {
    val sections = assigns.split(',').map { it.split('-') }.map { Pair(it[0].toInt(), it[1].toInt()) }
    val first = sections.minBy { it.first }
    val second = sections.maxBy { it.first }

    return first.second >=second.first
}
