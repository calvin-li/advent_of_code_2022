const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

data class Tree(val height: Int, var score: Int)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val trees = input.readLines().map{ i ->
        i.map{ j ->
            Tree(j - '0', 0)
        }.toTypedArray()
    }.toTypedArray()

    val numRows = trees.size
    val numCols = trees[0].size

    for (row in 1 until numCols-1) {
        for (col in 1 until numRows-1) {
            val tree = trees[row][col]
            tree.score = 1

            // look up
            var score = 1
            var x = row - 1
            while (trees[x][col].height < tree.height && x >= 1) {
                score += 1
                x -= 1
            }
            tree.score *= score

            // look down
            score = 1
            x = row + 1
            while (x < numRows-1 && trees[x][col].height < tree.height) {
                score += 1
                x += 1
            }
            tree.score *= score

            // look left
            score = 1
            var y = col - 1
            while (y > 0 && trees[row][y].height < tree.height) {
                score += 1
                y -= 1
            }
            tree.score *= score

            // look right
            score = 1
            y = col + 1
            while (y < numCols-1 && trees[row][y].height < tree.height) {
                score += 1
                y += 1
            }
            tree.score *= score
        }
    }

    if (testing)
    {
        trees.forEach { i ->
            i.forEach { j ->
                print("${j.height}|${j.score} ")
            }
            println()
        }
    }
    println(trees.maxOf { it.maxOf { j -> j.score } })
}
