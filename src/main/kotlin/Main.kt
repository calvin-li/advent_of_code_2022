const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

data class Cell(val y: Int, val x: Int, val height: Int, var min: Int)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    lateinit var end: Pair<Int, Int>
    val queue = mutableListOf<Cell>()

    val map = input.readLines().mapIndexed { i, s ->
        s.mapIndexed { j, c ->
            val height: Int
            when (c) {
                'S' -> {
                    height = 0
                }
                'E' -> {
                    height = 'z' - 'a'
                    end = Pair(i, j)
                }
                else -> {
                    height = c - 'a'
                }
            }
            val newCell = Cell(i, j, height, Int.MAX_VALUE)

            if (height == 0){
                newCell.min = 0
                queue.add(newCell)
            }

            newCell
        }
    }
    val width = map.first().size
    val height = map.size

    while (queue.isNotEmpty()){
        val current = queue.removeAt(0)
        val adj = mutableListOf<Pair<Int, Int>>()
        val y = current.y
        val x = current.x

        if (x>0) { adj.add(Pair(x-1, y)) }
        if (x<width-1) { adj.add(Pair(x+1, y)) }
        if (y>0) { adj.add(Pair(x, y-1)) }
        if (y<height-1) { adj.add(Pair(x, y+1)) }

        adj.map { map[it.second][it.first] }.forEach { a ->
            if (a.height <= current.height + 1 && a.min > current.min+1){
                a.min = current.min+1
                queue.add(a)
            }
        }
    }

    println(map[end.first][end.second].min)
}
