import kotlin.math.min
import java.util.PriorityQueue

const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val moves = input.readLines()

    val myMoves = moves.map { getMove(it.first(), it.last()) }
    val scores = moves.map { getScore(it.first(), getMove(it.first(), it.last())) }
    println(scores.sum())
}

fun getMove(theirMove: Char, strat: Char): Char{
    var delta = theirMove - 'A'
    delta += strat - 'Y'

    var move: Char = 'X' + delta

    if (move == 'X'-1){
        move = 'Z'
    }
    else if (move == 'Z'+1){
        move = 'X'
    }

    return move
}

fun getScore(theirMove: Char, myMove: Char): Int {
    val moveScore = mapOf<Char, Int>('X' to 1, 'Y' to 2, 'Z' to 3, 'A' to 1, 'B' to 2, 'C' to 3)

    val myScore = moveScore[myMove]!!
    val theirScore = moveScore[theirMove]!!
    var score: Int = myScore

    if (myScore == theirScore){
        score += 3
    }
    else if (myMove == 'X' && theirMove == 'C'){
        score += 6
    }
    else if (myMove == 'Z' && theirMove == 'A'){
        score += 0
    }
    else if (myScore > theirScore){
        score += 6
    }

    return score
}
