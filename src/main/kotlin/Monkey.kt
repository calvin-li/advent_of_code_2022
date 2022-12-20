class Monkey (source: List<String>) {
    fun runOp(item: Int): Long {
        val left = if (op[0] == "old") item else op[0].toInt()
        val action = if (op[1] == "*") {a: Long,b: Long -> a*b} else {a: Long,b: Long -> a+b}
        val right = if (op[2] == "old") item else op[2].toInt()

        return action(left.toLong(), right.toLong())
    }

    var items = source[1].substringAfter(": ").split(", ").map{ it.toInt()}.toMutableList()
    private val op = source[2].substringAfter(": new = ").split(' ')
    val test = source[3].substringAfterLast(' ').toInt()
    val tTarget = source[4].substringAfterLast(' ').toInt()
    val fTarget = source[5].substringAfterLast(' ').toInt()

    var inspections = 0
}