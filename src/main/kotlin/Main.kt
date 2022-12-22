const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val rawPackets = input.readLines().plus("[[2]]").plus("[[6]]").filterNot { it == "" }.map { j -> j.toMutableList() }
    val packets = rawPackets.map { i -> i.removeAt(0); parse(i)}

    val sorted = packets.sortedWith { l, r -> leq(l, r) }
    sorted.forEach { println(it) }
    print( sorted.findExtra(2) * sorted.findExtra(6))
}

private fun <E> List<E>.findExtra(i: Int): Int {
    return this.indexOfFirst {
        it is List<*> && it.size==1 && it[0] is List<*> && (it[0] as List<*>).size == 1 && (it[0] as List<*>)[0] == i
    } + 1
}

fun leq(left: MutableList<Any>, right: MutableList<Any>): Int {
    for(i in 0 until left.size){
        if (i >= right.size) { return 1}
        val l = left[i]
        val r = right[i]
        val innerLeq: Int
        if (l is Int && r is Int) {
            if (l < r) { return -1 }
            if (l > r) { return 1 }
        } else if (l is Int){
            innerLeq = leq(mutableListOf(l), r as MutableList<Any>)
            if (innerLeq != 0){ return innerLeq }
        } else if (r is Int){
            innerLeq = leq(l as MutableList<Any>, mutableListOf(r))
            if (innerLeq != 0){ return innerLeq }
        } else {
            innerLeq = leq(l as MutableList<Any>, r as MutableList<Any>)
            if (innerLeq != 0){ return innerLeq }
        }
    }
    return if (right.size > left.size) -1 else 0
}

fun parse(rawPacket: MutableList<Char>): MutableList<Any> {
    val parsed = mutableListOf<Any>()
    val num = mutableListOf<Char>()
    while (rawPacket.isNotEmpty()){
        when (val next = rawPacket.removeAt(0)) {
            '[' -> parsed.add(parse(rawPacket))
            ']' -> { addNum(num, parsed); return parsed }
            ',' -> addNum(num, parsed)
            else -> num.add(next)
        }
    }
    return parsed
}

private fun addNum(
    num: MutableList<Char>,
    parsed: MutableList<Any>
) {
    if (num.isNotEmpty()) {
        parsed.add(num.joinToString("").toInt())
        num.clear()
    }
}
