class Cave (private val min: XY, private val max: XY) {
    companion object {
        private const val SPACE = ' '
        private const val ROCK = '#'
        private const val SAND = '.'
        private const val SOURCE = '+'
        val SOURCEXY = XY(500, 0)
    }
    private val width = max.x - min.x + 1
    private val height = max.y - min.y + 1

    private val grid = Array(height) { Array(width){ SPACE }}
    var numSand = 0

    init {
        this[SOURCEXY] = SOURCE
    }

    operator fun get(col: Int, row: Int): Char {
        return grid[row-min.y][col-min.x]
    }

    operator fun set(col: Int, row: Int, value: Char) {
        grid[row-min.y][col-min.x] = value
    }

    private operator fun get(xy: XY): Char {
        return this[xy.x, xy.y]
    }

    private operator fun set(xy: XY, value: Char) {
        this[xy.x, xy.y] = value
    }

    fun add(rock: List<XY>) {
        for (i in 1 until rock.size){
            val line = setOf(rock[i-1], rock[i])
            val x = line.map { it.x }
            val y = line.map { it.y }
            val points = mutableListOf<XY>()

            for (j in x.min()..x.max()){
                for (k in y.min()..y.max()){
                    points.add(XY(j, k))
                }
            }
            points.forEach {
                this[it] = ROCK
            }
        }
    }

    fun smooth(){
        val vert = '|'
        val horz = '-'
        val cross = '+'

        for (j in min.y+1 until max.y){
            for (i in min.x+1 until max.x) {
                if (this[i, j] in listOf(ROCK, vert, horz, cross)) {
                    val left = i - 1
                    val right = i + 1
                    val up = j - 1
                    val down = j + 1

                    var h = false
                    var v = false

                    if (this[left, j] != SPACE || this[right, j] != SPACE) {
                        h = true
                    }
                    if (this[i, up] != SPACE || this[i, down] != SPACE) {
                        v = true
                    }

                    var new = ROCK
                    if (h && v) {
                        new = cross
                    } else if (h) {
                        new = horz
                    } else if (v) {
                        new = vert
                    }
                    this[i, j] = new
                }
            }
        }
    }

    fun sim() {
        var done = false
        var curX = SOURCEXY.x
        var curY = SOURCEXY.y

        while (!done){
            if (this[SOURCEXY] != SOURCE){
                done = true
            }else if (curY == height-1){
                this[curX, curY] = SAND
                curX = SOURCEXY.x
                curY = SOURCEXY.y
            }else if (this[curX, curY+1] == SPACE){
                curY += 1
            }else if (this[curX-1, curY+1] == SPACE){
                curX -= 1
                curY += 1
            }else if (this[curX+1, curY+1] == SPACE){
                curX += 1
                curY += 1
            } else {
                this[curX, curY] = SAND
                curX = SOURCEXY.x
                curY = SOURCEXY.y
                numSand += 1
            }
        }
    }

    fun print(){
        val w = if (max.y > 100) 3 else if (max.y > 10) 2 else 1
        val h = 3

        val xAxis = (min.x/5 until max.x/5).map {(it+1)*5}.plus(min.x).plus(max.x)

        for (i in 0 until h){
            print(" ".repeat(w+1))
            val xAxisStr = Array(width) {' '}
            xAxis.forEach {
                xAxisStr[it-min.x] = it.toString()[i]
            }
            println(xAxisStr.joinToString(""))
        }

        for (j in min.y .. max.y){
            print(j.toString().padStart(w).padEnd(w+1, ' '))
            for (i in min.x .. max.x){
                if (j == max.y){
                    print(ROCK)
                } else {
                    print(this[i,j])
                }
            }
            println()
        }
    }
}