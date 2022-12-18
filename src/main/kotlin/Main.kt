const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    println(input.readLines())
}
