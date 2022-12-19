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
        display(rope)
    }



    println(visited.size)
}

fun display(rope: Array<Pos>) {
    val leftBound = kotlin.math.min(rope.minOf { it.x } - 1, -1)
    val rightBound = rope.maxOf { it.x } + 1
    val upBound = rope.maxOf { it.y } + 1
    val downBound = kotlin.math.min(rope.minOf { it.y } - 1, -1)

    val grid = Array(upBound - downBound + 1){ Array(rightBound - leftBound + 1){'.'} }
    grid[-downBound][-leftBound] = 's'

    for(i in rope.size-1 downTo 0){
        val pos = rope[i]
        grid[-downBound + pos.y][-leftBound + pos.x] = '0' + i
    }

    grid.forEach {
        it.forEach { i -> print(i) }
        println()
    }
    println("---------------")
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

    val moveTo = Pos(head.x, head.y)

    head.x += movement.x
    head.y += movement.y

    rope.dropLast(1).forEachIndexed { index, prev ->
        val next = rope[index+1]
        if (kotlin.math.abs(prev.x - next.x) > 1 || kotlin.math.abs(prev.y - next.y) > 1) {
            val temp = Pos(next.x, next.y)
            next.x = moveTo.x
            next.y = moveTo.y

            moveTo.x = temp.x
            moveTo.y = temp.y
        }
    }

    visited.add(Pair(tail.x, tail.y))
}
