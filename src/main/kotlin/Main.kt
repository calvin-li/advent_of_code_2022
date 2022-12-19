import kotlin.math.max

const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

data class Pos(var x: Int, var y: Int)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val moves = input.readLines().map{
        Pair(it.split(' ')[0].first(), it.split(' ')[1].toInt())
    }

    val rope = Array(10){ Pos(0, 0)}
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(Pair(0, 0))

    moves.forEach {
        val direction = it.first
        val length = it.second

        (1..length).forEach { _ ->
            move(direction, rope, visited)
        }
        if (testing) display(rope)
    }


    println(visited.size)
}

fun move(direction: Char, rope: Array<Pos>, visited: MutableSet<Pair<Int, Int>>) {
    val movement: Pos = when (direction){
        'U' -> Pos(0, 1)
        'D' -> Pos(0, -1)
        'L' -> Pos(-1, 0)
        'R' -> Pos(1, 0)
        else -> {throw IllegalArgumentException()}
    }
    val head = rope.first()
    val tail = rope.last()

    head.x += movement.x
    head.y += movement.y

    rope.dropLast(1).forEachIndexed { index, prev ->
        val next = rope[index+1]
        val diff = Pos(prev.x - next.x, prev.y - next.y)
        if (kotlin.math.abs(diff.x) > 1 || kotlin.math.abs(diff.y) > 1) {
            // need to move
            if (diff.x == 0 || diff.y == 0){
                // need to move vertically or horizontally
                if (diff.x == 0){
                    next.y += diff.y/2
                }
                else {
                    next.x += diff.x/2
                }
            } else {
                // need to move diagonally
                if (diff.x > 0){
                    next.x += 1
                } else {
                    next.x -= 1
                }
                if (diff.y > 0){
                    next.y += 1
                } else {
                    next.y -= 1
                }
            }
        }
    }

    visited.add(Pair(tail.x, tail.y))
}

fun display(rope: Array<Pos>) {
    val leftBound = kotlin.math.min(rope.minOf { it.x } - 1, -1)
    val rightBound = max(rope.maxOf { it.x } + 1, 1)
    val upBound =  max(rope.maxOf { it.y } + 1, 1)
    val downBound = kotlin.math.min(rope.minOf { it.y } - 1, -1)

    val grid = Array(upBound - downBound + 1){ Array(rightBound - leftBound + 1){'.'} }
    grid[grid.size-1 + downBound][-leftBound] = 's'

    for(i in rope.size-1 downTo 0){
        val pos = rope[i]
        val marker = if (i==0) 'H' else '0' + i
        grid[grid.size-1 + downBound - pos.y][-leftBound + pos.x] = marker
    }

    grid.forEachIndexed { idx, it ->
        it.forEach { i -> print(i) }
        println()
    }
    println("---------------")
}
