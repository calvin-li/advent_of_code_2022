const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val ops = input.readLines()
    val cycles = mutableListOf<Int>()
    cycles.add(1)

    ops.forEach {
        val x = cycles.last()
        cycles.add(x)
        if (it.startsWith("addx")){
            cycles.add(x + it.split(' ')[1].toInt())
        }
    }

    val peaks = (0..5).map { it * 40 + 20 }

    val crtRows = 6
    val crtCols = 40
    val crt = Array(crtRows){ CharArray(crtCols) }

    cycles.forEachIndexed { i, _ ->
        if (i > 0) {
            val c = i-1
            val x = cycles[c]
            val h = c % crtCols
            val pixel = if (h in (x-1..x+1)) '#' else '.'

            crt[c/crtCols][h] = pixel
        }
    }

    crt.forEach { println(((it.map{ i-> "$i$i"}.reduce{a, n -> a + n}))) }
}
