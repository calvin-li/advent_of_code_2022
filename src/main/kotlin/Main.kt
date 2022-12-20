const val filename = "input.txt"
const val testFile = "test.txt"

val testing = System.getenv("TEST") == "True"
val input = Reader(if (testing) testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val monkeys = input.readLines().chunked(7).map { Monkey(it) }

    (1..10000).forEach{ _ -> round(monkeys)}

    println(monkeys.map{it.inspections.toLong()}.sorted().takeLast(2).reduce { acc, i -> acc*i })
}

fun round(monkeys: List<Monkey>) {
    val coprime = monkeys.map{it.test}.reduce { acc, i -> acc*i }

    monkeys.forEach { m ->
        m.items.forEach { i ->
            val newValue: Int = (m.runOp(i) % coprime).toInt()

            val target = if (newValue % m.test == 0) m.tTarget else m.fTarget
            monkeys[target].items.add(newValue)
        }

        m.inspections += m.items.size
        m.items.clear()
    }
}


