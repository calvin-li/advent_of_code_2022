const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val message: String = input.readAll()
    val elfString = (message.split("\n\n").map { i -> i.split("\n").filter{ j -> j != ""}})
    val elves = elfString.map { it.sumOf { i -> i.toInt() } }.sorted()

    println(elves.takeLast(3).sum())
}
