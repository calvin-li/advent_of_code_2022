const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

data class Tree(val height: Int, var score: Int)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val trees = input.readLines().map{ i ->
        i.map{ j ->
            Tree(j - '0', 1)
        }.toTypedArray()
    }.toTypedArray()

    val numRows = trees.size
    val numCols = trees[0].size

    for (row in 0 until numCols) {
        for (col in 0 until numRows) {
            val tree = trees[row][col]

            // look up
            var score = 0
            var x = row - 1
            while (x >= 0 && trees[x][col].height < tree.height) {
                score += 1
                x -= 1
            }
            tree.score *= score

            // look down
            score = 0
            x = row + 1
            while (x < numRows && trees[x][col].height < tree.height) {
                score += 1
                x += 1
            }
            tree.score *= score

            // look left
            score = 0
            var y = col - 1
            while (y >= 0 && trees[row][y].height < tree.height) {
                score += 1
                y -= 1
            }
            tree.score *= score

            // look right
            score = 0
            y = col + 1
            while (y > numCols && trees[row][y].height < tree.height) {
                score += 1
                y += 1
            }
            tree.score *= score
        }
    }

    println(trees)
}
